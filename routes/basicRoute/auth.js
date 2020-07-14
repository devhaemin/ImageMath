const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');
const jwt = require('jsonwebtoken');
const crypto = require('crypto');
const mysql = require('mysql');
const fs = require('fs');
const connection = cUtil.connection;


function crypPw(password) {
    return new Promise(function (resolve, reject) {
        var salt = "";
        var newPw;
        crypto.randomBytes(64, function (err, buf) {
            if (err) {
                console.error(err);
                res.status(500).send('500 SERVER ERROR');
            } else {
                salt = buf.toString('base64');
                crypto.pbkdf2(password, salt, 98523, 64, 'sha512', function (error, key) {
                    if (error) {
                        console.log(error);
                        res.status(400).send("crypto error");
                    } else {
                        newPw = key.toString('base64');
                    }
                })
            }
        })
        setTimeout(() => {
            resolve([salt, newPw]);
        }, 500);
    })
}

function pwBySalt(password, salt) {
    return new Promise(function (resolve, reject) {
        crypto.pbkdf2(password, salt, 98523, 64, 'sha512', function (err, key) {
            resolve(key.toString('base64'));//replace추가
        })
    })
}


router.post('/login', obtainToken);
//router.get('/refresh', refreshToken);
router.post('/register', registerUser);
//router.post('/changepw', changepw);
router.get('/autologin', autologin);//만들어야할 api

router.get('/terms', getTerms);


function getTerms(req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'}); // header 설정
    fs.readFile('../../views/terms.html', (err, data) => { // 파일 읽는 메소드
        if (err) {
            return console.error(err); // 에러 발생시 에러 기록하고 종료
        }
        res.end(data, 'utf-8'); // 브라우저로 전송
    });
}

function obtainToken(req, res) {
    console.log("post login request");
    const email = req.body.email;
    const reqPw = req.body.password;

    const sql = "SELECT * FROM UserInfo WHERE email = ?";
    const sql4 = "update UserInfo set accessToken = ? where email = ?";
    const sql1 = mysql.format(sql, email);
    connection.query(sql1, function (err, rows, fields) {
        if (err) {
            console.log("SELECT ERROR : " + err);
            res.status(500).send('500 SERVER ERROR');
        } else if (rows.length == 0) {
            console.log("NO ACCOUNT");
            res.status(204).send('NO ACCOUNT');
        } else {
            const dbPw = rows[0].password;
            const salt = rows[0].salt;
            pwBySalt(reqPw, salt).then(function (resolve) {
                const crypReqPw = resolve;
                if (dbPw != crypReqPw) {
                    console.log("uncorrect pw : " + resolve);
                    res.status(204).send('UNCORRECT PW');
                } else {
                    var accessToken = "";
                    const options = {expiresIn: 60 * 60 * 24 * 7};
                    var timestamp = new Date().getTime();
                    console.log("timeStamp : " + timestamp);
                    var token = jwt.sign(email, timestamp.toString(16), {
                        algorithm: 'HS256'
                    });
                    var accessTokenSubStr = token.substr(0, 64);
                    connection.query(sql4, [accessTokenSubStr, email], function (error, results, nexts) {
                        if (error) {
                            console.log(error);
                            res.status(400).send("insert token error");
                        } else {
                            console.log("insert token");
                            connection.query(sql, email, function (errors, result, field) {
                                if (errors) {
                                    console.log("select error");
                                    res.status(400).send("token error");
                                } else {
                                    console.log("login success");
                                    res.status(200).send(result[0]);
                                }
                            })
                        }
                    })
                    /*
                    jwt.sign(payload, timestamp, options, function (err, token) {//이 함수만 문제가 있는듯
                        if (err) {
                            console.log("토큰 생성 오류");
                            res.status(500).send('토큰 생성 오류');
                        } else {
                            console.log("토큰생성");
                            accessToken = token.toString('base64');
                            console.log(accessToken);
                            var param = [accessToken, email];
                            connection.query(sql4, param, function (error, result, field) {
                                if (error) {
                                    console.log(error);
                                    res.status(400).send("accessToken error");
                                } else {
                                    console.log("accessToken insert success");
                                    res.status(200).send(rows);
                                }
                            })
                        }
                    });
                     */
                }
            })
        }
    })
}

/*
function refreshToken(req, res) {

    const token = req.headers['x-access-token'];

    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    }
    ;

    const secretKey1 = "stu";
    const secretKey2 = "tut"
    let whichOne = true;
    jwt.verify(token, secretKey1, function (err, decodedToken) {
        if (err) {
            jwt.verify(token, secretKey2, function (err, decodedToken) {
                if (err) {
                    res.status(400).send('FALSE TOKEN3');
                }
                req.decoded = decodedToken;
                whichOne = false;
            })
        } else {
            req.decoded = decodedToken;
        }
    })

    let sql = "SELECT userSeq FROM UserInfo WHERE email = ? AND userSeq = ?";
    if (whichOne == false) {
        sql = "SELECT userSeq FROM TutorPrivacy WHERE email = ? AND userSeq = ?";
    }
    const param = [req.decoded.email, req.decoded.userSeq];

    util.connection.query(sql, param, function (err, rows, field) {
        if (err) {
            console.error(err.stack);
            res.status(500).send('500 SERVER ERROR, db7');
        } else if (row.length != 1) {
            res.status(400).send('FALSE TOKEN4');
        } else {
            const payload = {
                email: req.decoded.email,
                userSeq: rows[0].userSeq
            };
            const options = {expiresIn: 60 * 60 * 24 * 7};

            jwt.sign(payload, options, req.decoded.secret, function (err, token) {
                if (err) {
                    console.error(err.stack);
                    res.status(500).send('500 SERVER ERROR, db5');
                } else {
                    res.status(200).send(token);
                }
            });
        }
    })
}
 */

function registerUser(req, res) {
    var array = {
        name: req.body.name,
        email: req.body.email,
        passwords: req.body.password,
        birthday: req.body.birthday,
        //  schoolSeq: req.body.schoolSeq,
        //       reqLectureSeqs: req.body.reqLectureSeqs,
        gender: req.body.gender,
        userType: req.body.userType,
        phone: req.body.phone,
        schoolName: req.body.schoolName,
        studentCode: req.body.studentCode,
        registerTime: Date.now()
    }
    console.log("array : " + array);
    // all conptent delievered
    if (!cUtil.isDelivered(array)) {
        res.status(400).send("ALL CONTENTS ARE NOT DELIVERED");
    } else {
        console.log("All contents are delivered");
    }
    const sql1 = "SELECT userSeq FROM UserInfo WHERE email = ?;";
    // serach multiple register
    connection.query(sql1, array.email, function (err, rows, field) {
        if (err) {
            console.log("check info before insert");
            console.log(err);
            res.status(500).send('500 SERVER ERROR, db1');
        }
        //�ٲٱ�
        else if (rows.length != 0) {
            console.log("ALREADY EXIST ACCOUNT");
            res.status(400).send('ALREADY EXIST ACCOUNT');
        }
        // make salt and hash again
        else {
            crypPw(array.passwords)
                .then(function (resolve) {
                    const salt = resolve[0];
                    const hsPw = resolve[1];
                    const sql2 = "INSERT INTO UserInfo SET ? ;";
                    var accessToken = "";
                    const options = {expiresIn: 60 * 60 * 24 * 7};
                    var timestamp = new Date().getTime();
                    var token = jwt.sign(req.body.email, timestamp.toString(16), {
                        algorithm: 'HS256'
                    });
                    var accessTokenSubStr = token.substr(0, 64);
                    var param2 = {
                        name: array.name,
                        email: array.email,
                        password: hsPw,
                        birthday: array.birthday,
                        //schoolSeq: 1,
                        gender: array.gender,
                        userType: array.userType,
                        studentCode: array.studentCode,
                        schoolName: array.schoolName,
                        //lectureSeqs: "0/0",
                        phone: array.phone,
                        accessToken: accessTokenSubStr,
                        //reqLectureSeqs: array.reqLectureSeqs,
                        salt: salt,
                        registerTime: array.registerTime
                    };
                    /*
                    if (req.userType == "tutor") {
                        param2.lectureSeqs = -1;
                    }
                    */
                    const sqls2 = mysql.format(sql2, param2);
                    //data insert
                    connection.query(sqls2, function (err, rows, fields) {
                        if (err) {
                            console.log("insert query error")
                            console.log(err);
                            res.status(500).send('500 SERVER ERROR, db3');
                        } else {
                            console.log('REGISTER SUCCESS');
                        }
                    })
                    //var reqlecarr = array.reqLectureSeqs.split('/');
                    var timestamp = new Date().getTime();
                    connection.query("select * from UserInfo where email = ?", array.email, function (error, result, next) {
                        if (error) {
                            console.log(error);
                            console.log("reqLectureSeq error");
                        } else {
                            /*
                              for(var i=0;i<reqlecarr.length-2;i++){
                                  var params3 = {
                                      userSeq: result[0].userSeq,
                                      lectureSeq: reqlecarr[i+1],
                                      time: timestamp,
                                      schoolName: array.schoolSeq
                                  }
                                  connection.query("insert into Recognition set ?", params3, function(err, results, next){
                                      if(err){
                                          console.log(err);
                                          console.log("reqlecture insert error");
                                      }
                                      else{
                                          console.log("insert reqlecture : " + reqlecarr[i+1]);
                                      }
                                  })
                              }
                              */
                            res.status(200).send(result[0]);
                        }
                    })
                })
        }
    })
}

/*8
function changepw(req, res) {
    const userSeq = req.decoded.id;
    const email = req.decoded.email;

    const password = req.body.password;

    crypPw(password)
        .then(function (resolve) {

            const salt = resolve[0];
            const hsPw = resolve[1];

            const sql1 = "UPDATE UserInfo (password, salt) VALUES (?,?) WHERE userSeq = ? AND email = ? ";
            const param1 = [hsPw, salt, userSeq, email];
            const sqls1 = mysql.format(sql1, param1);

            const sql2 = "UPDATE ? (password, salt) VALUES (?,?) WHERE userSeq = ? AND email = ?";
            let param2;
            if (req.secret == secretKey1) {
                param2 = ["StudentPrivacy", hsPw, salt, userSeq, email];
            } else if (req.secret == secretKey2) {
                param2 = ["TutorPrivacy", hsPw, salt, userSeq, email];
            } else {
                res.status(400).send('TOKEN ERROR');
            }
            const sqls2 = mysql.format(sql2, param2);

            connection.query(sqls1 + sqls2, function (err, rows, field) {

                if (err) {
                    console.error(err.stack);
                    res.status(500).send('500 SERVER ERROR, db1');
                }
                //�ٲٱ�
                else if (rows.length != 1) {
                    res.status(204).send('NO EXIST ACCOUNT');
                } else {
                    res.status(200);
                }

            })

        })
}
 */

function autologin(req, res) {
    const token = req.headers['x-access-token'];
    const sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    connection.query(sql1, function (err, result, next) {
        if (err) {
            console.log(err);
            var object = {
                msg: "토큰이 만료되었습니다.",
                code: 400
            }
            res.status(400).json(object);
        } else if (result.length == 0) {
            var object = {
                msg: "토큰이 만료되었습니다.",
                code: 400
            }
            res.status(400).json(object);
        } else {
            console.log("auto login");
            console.log(result);
            res.status(200).send(result[0]);
        }
    })
}

module.exports = router;
