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
        let salt = "";
        let newPw;
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
router.get('/autologin', autoLogin);//만들어야할 api

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

/**
 * @api {post} auth/login 이메일로 토큰 발급하기
 * @apiName ObtainToken with email login
 * @apiGroup Auth
 * @apiPermission normal
 * @apiParam {String} email
 * @apiParam {String} password
 *
 * @apiUse UserInfo
 *
 * @apiSuccessExample {json} Success-Response :
 *      HTTP/1.1 200 OK
 *      {
 *          "userSeq": 15,
 *          "name": "조교",
 *          "birthday": "2000-12-12",
 *          "email": "tutor@gmail.com",
 *          "password": "~~~",
 *          "accessToken": "eyJhbGciOiJIUzK3OPQ9.dHV0b3JAZ21haWwuY29t.fI1CeHpLshyHcLMf3M06US",
 *          "fcmToken": null,
 *          "gender": 0,
 *          "salt": "~~~",
 *          "userType": "tutor",
 *          "phone": "01012345678",
 *          "studentCode": "0000000",
 *          "schoolName": "00고",
 *          "registerTime": 1589603903081
 *      }
 */

function obtainToken(req, res) {
    const email = req.body.email;
    const reqPw = req.body.password;

    const sql = "SELECT * FROM UserInfo WHERE email = ?";
    const sql4 = "update UserInfo set accessToken = ? where email = ?";
    const sql1 = mysql.format(sql, email);
    connection.query(sql1, function (err, rows) {
        if (err) {
            console.log("SELECT ERROR : " + err);
            res.status(500).send('500 SERVER ERROR');
        } else if (rows.length === 0) {
            console.log("NO ACCOUNT");
            res.status(204).send('NO ACCOUNT');
        } else {
            const dbPw = rows[0].password;
            const salt = rows[0].salt;
            pwBySalt(reqPw, salt).then(function (resolve) {
                const crypReqPw = resolve;
                if (dbPw !== crypReqPw) {
                    console.log("uncorrect pw : " + resolve);
                    res.status(204).send('UNCORRECT PW');
                } else {
                    let timestamp = new Date().getTime();
                    console.log("timeStamp : " + timestamp);
                    let token = jwt.sign(email, timestamp.toString(16), {
                        algorithm: 'HS256'
                    });
                    let accessTokenSubStr = token.substr(0, 64);
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
                }
            })
        }
    })
}

/**
 * @api {post} auth/register 회원가입
 * @apiName Register with email
 * @apiGroup Auth
 * @apiPermission normal
 * @apiParam {String} email
 * @apiParam {String} password
 * @apiParam {String} name
 * @apiParam {String} birthday
 * @apiParam {Int} gender 0: 남자, 1: 여자
 * @apiParam {String} userType student : 학생 tutor : 조교
 * @apiParam {String} phone 휴대폰 번호
 * @apiParam {String} schoolName 학교 이름 ex)대성고
 * @apiParam {String} studentCode 학생 스쿨링코드
 *
 * @apiUse UserInfo
 */

function registerUser(req, res) {
    let array = {
        name: req.body.name,
        email: req.body.email,
        password: req.body.password,
        birthday: req.body.birthday,
        gender: req.body.gender,
        userType: req.body.userType,
        phone: req.body.phone,
        schoolName: req.body.schoolName,
        studentCode: req.body.studentCode,
        registerTime: Date.now()
    }
    if (!cUtil.isDelivered(array)) {
        res.status(400).send("ALL CONTENTS ARE NOT DELIVERED");
    } else {
        console.log("All contents are delivered");
    }
    const sql1 = "SELECT userSeq FROM UserInfo WHERE email = ?;";
    connection.query(sql1, array.email, function (err, rows, field) {
        if (err) {
            console.log("check info before insert");
            console.log(err);
            res.status(500).send('500 SERVER ERROR, db1');
        } else if (rows.length !== 0) {
            console.log("ALREADY EXIST ACCOUNT");
            res.status(400).send('ALREADY EXIST ACCOUNT');
        } else {
            crypPw(array.password)
                .then(function (resolve) {
                    const salt = resolve[0];
                    const hsPw = resolve[1];
                    const sql2 = "INSERT INTO UserInfo SET ?;";
                    let timestamp = new Date().getTime();
                    let token = jwt.sign(req.body.email, timestamp.toString(16), {
                        algorithm: 'HS256'
                    });
                    let isSuccess = true;
                    let accessTokenSubStr = token.substr(0, 64);
                    let param2 = {
                        name: array.name,
                        email: array.email,
                        password: hsPw,
                        birthday: array.birthday,
                        gender: array.gender,
                        userType: array.userType,
                        studentCode: array.studentCode,
                        schoolName: array.schoolName,
                        phone: array.phone,
                        accessToken: accessTokenSubStr,
                        salt: salt,
                        registerTime: array.registerTime
                    };
                    const sqls2 = mysql.format(sql2, param2);
                    connection.query(sqls2, function (err, rows, fields) {
                        if (err) {
                            console.log("insert query error")
                            console.log(err);
                            isSuccess = false;
                        } else {
                            console.log('REGISTER SUCCESS');
                        }
                    })
                    connection.query("select * from UserInfo where email = ?", array.email, function (error, result, next) {
                        if (error) {
                            console.log(error);
                            console.log("reqLectureSeq error");
                            isSuccess = false;
                        } else {
                            if(isSuccess)
                                res.status(200).send(result[0]);
                            else
                                res.status(500).send({});
                        }
                    })
                })
        }
    })
}

/**
 * @api {get} auth/autologin 토큰 로그인
 * @apiName ObtainToken with email login
 * @apiGroup Auth
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 * @apiUse UserInfo
 */

function autoLogin(req, res) {
    const userInfo = req.userInfo;

    if (!userInfo) {
        res.status(403).send({msg : "토큰이 만료되었습니다.", code : 400})
    } else {
        res.status(200).send(userInfo);
    }
}

module.exports = router;
