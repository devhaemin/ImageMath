const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');
const mysql = require('mysql');
const connection = cUtil.connection;
cUtil.connect;

function deleteLecture(index, reqLectureSeqs, userSeq, lectureSeq) {
    var lectureList = reqLectureSeqs.split('/');
    if (lectureList[index] == lectureSeq) {
        lectureList.splice(index, 1);
        var joinList = lectureList.join('/');
        console.log("after delete reqLectureSeq : " + joinList);
        var sql = "update UserInfo set reqLectureSeqs = ? where userSeq = ?";
        var sql1 = mysql.format(sql, [joinList, userSeq]);
        console.log("update sql : " + sql1);
        setTimeout(() => {
            connection.query(sql1, function (err, result, next) {
                if (err) {
                    console.log(err);
                } else {
                    return;
                }
            })
        }, 200);
    }
}

router.get('/', lecture);
router.get('/student', studentLectureList);
router.get('/student/:lectureSeq', getStudentList);
router.patch('/recognition/:lectureSeq', recognitionLec);
router.get('/student/:lectureSeq', lecturestu);
router.delete('/student/:lectureSeq/:userSeq', deleteStudent);
router.post('/add', addlecture)
router.get('/recognition/:lectureSeq', getRecongition);
router.post('/add/student', addrecognition);
router.get('/tutor', getStudentLectureList);

function getStudentList(req, res) {
    var token = req.headers['x-access-token'];
    var lectureSeq = req.params.lectureSeq;

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length == 0 || result[0].userType == "student") {
            console.log("잘못된 토큰입니다.");
            res.status(400).send("잘못된 토큰입니다.");
        } else {
            connection.query("select * from LectureAdm as la JOIN UserInfo as ui where la.lectureSeq = ? and la.userSeq = ui.userSeq", [lectureSeq], function (err, studentInfos) {
                if (err) {
                    console.log(err);
                    res.status(400);
                } else {
                    res.status(200).send(studentInfos);
                }
            })
        }
    });

}

function deleteStudent(req, res) {
    var token = req.headers['x-access-token'];
    var lectureSeq = req.params.lectureSeq;
    var userSeq = req.params.userSeq;

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length == 0 || result[0].userType == "student") {
            console.log("잘못된 토큰입니다.");
            res.status(400).send("잘못된 토큰입니다.");
        } else {
            connection.query("delete from LectureAdm where lectureSeq = ? and userSeq = ?", [lectureSeq, userSeq], function (err, nope) {
                if (err) {
                    console.log(err);
                    res.status(400);
                } else {
                    res.status(200);
                }
            })
        }
    });

}

function getStudentLectureList(req, res) {

    var token = req.headers['x-access-token'];
    var userSeq = req.query.userSeq;
    var sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    connection.query(sql1, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length == 0 || result[0].userType == "student") {
            console.log("잘못된 토큰입니다.");
            res.status(400).send("잘못된 토큰입니다.");
        } else {
            connection.query("select * from LectureAdm AS la JOIN LectureInfo AS li ON la.lectureSeq = li.lectureSeq and la.userSeq = ?", userSeq, function (err, lectureList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error");
                } else {
                    res.status(200).send(lectureList);
                }
            });
        }
    });
}

function lecture(req, res) {
    var sql = "select * from LectureInfo order by lectureSeq DESC";
    connection.query(sql, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("lecture search error");
        } else {
            console.log("lecture List");
            res.status(200).send(result);
        }
    })
}

function studentLectureList(req, res) {
    console.log("studentLectureList");
    const token = req.headers['x-access-token'];
    const page = req.query.page;
    var sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    connection.query(sql1, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length == 0) {
            console.log("잘못된 토큰입니다.");
            res.status(400).send("잘못된 토큰입니다.");
        } else if (result[0].userType == "student") {
            var sql2 = "select lectureSeq from LectureAdm where userSeq = ? order by lectureSeq desc";
            connection.query(sql2, result[0].userSeq, function (error, results, nexts) {
                if (error) {
                    console.log(error);
                    res.status(400).send("수업 시퀀스 에러");
                } else {
                    var ret = [];
                    var length = results.length;

                    var idx = 0;
                    if (length == 0) {
                        res.status(200).send([]);
                    }
                    for (var i = 0; i < length; i++) {
                        var sql3 = 'select * from LectureInfo where lectureSeq = ?';
                        connection.query(sql3, results[i].lectureSeq, function (error, results, nexts) {
                            ret.push(results[0]);

                            if (idx == length - 1) {
                                res.status(200).send(ret);
                                console.log(result[0].userSeq + "의 수업목록을 조회합니다.");
                            }
                            idx++;
                        });
                    }
                }
            })
        } else {
            console.log("permission error : " + result[0].userType);
            res.status(400).send("권한이 없습니다.");
        }

    })

}

function recognitionLec(req, res) {
    const token = req.headers['x-access-token'];
    var lectureSeq = req.params.lectureSeq;
    var studentSeq = req.body.studentSeq;
    var isAccept = req.body.isAccept;
    var sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    console.log("lectureSeq = " + lectureSeq);
    connection.query(sql1, function (err, results, next) {
        if (err || results.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (results[0].userType = "tutor") {
            connection.query("select * from UserInfo AS ui JOIN LectureInfo AS li where ui.userSeq = ? and li.lectureSeq = ?", [studentSeq, lectureSeq], function (err, result, next) {
                if (err) {
                    console.log(err);
                    //res.status(400).send("학생을 찾을 수 없습니다.");
                } else if (result.length == 0) {
                    console.log("select * from UserInfo where userSeq = " + studentSeq);
                    res.status(400).send("학생을 찾을 수 없습니다.");
                } else {
                    if (isAccept) {
                        var timestamp = new Date().getTime();
                        var params = {
                            userSeq: result[0].userSeq,
                            lectureSeq: lectureSeq,
                            time: timestamp,
                        }
                        connection.query("insert into LectureAdm set ?", params, function (error, results, nexts) {
                            if (error) {
                                console.log(error);
                            } else {
                                console.log("insert success");

                                cUtil.sendPushMessage(studentSeq, result[0].name + "", "수업 추가 요청이 승인되었습니다.");
                                res.status(200).send("인증이 완료되었습니다.");
                            }
                        });
                        connection.query("update LectureInfo set studentNum = studentNum+1 where lectureSeq = ?", lectureSeq, function (err, result, next) {
                            if (err) {
                                console.log(err);
                            }
                        });

                        connection.query("delete from Recognition where userSeq = ? and lectureSeq = ?", [result[0].userSeq, lectureSeq], function (err, result, next) {
                            if (err) {
                                console.log("delete error");
                                console.log(error);
                            } else {
                                console.log("delete Recognition");
                            }
                        })

                        connection.query("INSERT IGNORE INTO AssignmentAdm (userSeq, assignmentSeq, uploadTime) SELECT userSeq,assignmentSeq,uploadTime FROM LectureAdm as la JOIN AssignmentInfo AS ai JOIN (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000) AS uploadTime) AS time where la.lectureSeq = ai.lectureSeq;"
                            , function (err, result) {
                                if (err) {
                                    console.log("add assignment error!");
                                }
                            });
                    } else {
                        connection.query("delete from Recognition where userSeq = ? and lectureSeq = ?", [result[0].userSeq, lectureSeq], function (err, result, next) {
                            if (err) {
                                console.log("delete error");
                                console.log(error);
                            } else {
                                console.log("delete Recognition");
                            }
                        })

                        res.status(200).send("인증이 취소되었습니다.");
                    }
                }
            })
        } else {
            console.log("permission error");
            res.status(400).send("권한이 없습니다.");
        }
    })
}


function lecturestu(req, res) {
    const token = req.headers['x-access-token'];
    const page = req.query.page;
    const lectureSeq = req.params.lectureSeq;

    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        var sql1 = "select * from UserInfo where accessToken = ?";
        connection.query(sql1, token, function (err, result, next) {
            if (err || result.length == 0) {
                console.log(err);
                res.status(400).send("토큰이 만료되었습니다.");
            } else if (result[0].userType == "student") {
                console.log("Token Error");
                res.status(400).send("token error");
            } else {
                connect.query("select * from LectureAdm where lectureSeq = ?", lectureSeq, function (err, userInfos) {
                    if (err) {
                        console.log(err);
                        res.status(400).send("Query Error");
                    } else {
                        res.status(200).send(userInfos);
                    }
                })
            }
        });
    }
}

function addlecture(req, res) {
    const token = req.headers['x-access-token'];
    const academySeq = req.body.academySeq;
    const academyName = req.body.academyName;
    const name = req.body.name;
    const weekDay = req.body.weekDay;
    const time = req.body.time;
    const totalDate = req.body.totalDate;
    const week = req.body.week;
    const studentNum = req.body.studentNum;

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result[0].userType = "tutor") {
            sql = "INSERT INTO LectureInfo SET ?"
            param = {
                academySeq: academySeq,
                academyName: academyName,
                name: name,
                weekDay: weekDay,
                time: time,
                week: week,
                totalDate: totalDate,
                studentNum: studentNum
            }

            connection.query(sql, param, function (err, rows, field) {
                if (err) {
                    console.error(err);
                    res.status(500).send('500 SERVER ERROR');
                } else {
                    console.log("lectureadd");
                    res.status(200).send("수업이 개설되었습니다.");
                }
            })
        } else {
            console.log("permission error");
            res.status(400).send("권한이 없습니다");
        }
    })
}

function getRecongition(req, res) {
    const token = req.headers['x-access-token'];
    const lectureSeq = req.params.lectureSeq;
    const page = req.query.page;
    var sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    connection.query(sql1, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result[0].userType == "tutor") {
            var sql2 = "select * from Recognition where lectureSeq = ? ";
            connection.query(sql2, lectureSeq, function (error, results, nexts) {
                if (error) {
                    console.log(error);
                    res.status(400).send("수업 시퀀스 에러");
                } else {
                    res.status(200).send(results);
                }
            })
        } else {
            console.log("permission error : " + result[0].userType);
            res.status(400).send("권한이 없습니다.");
        }
    })
}

function addrecognition(req, res) {
    const token = req.headers['x-access-token'];
    const lectureSeq = req.body.lectureSeq;
    const lectureName = req.body.lectureName;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            var userSeq = result[0].userSeq;
            connection.query("SELECT lectureSeq FROM Recognition WHERE userSeq = ? AND lectureSeq = ? UNION SELECT lectureSeq FROM LectureAdm WHERE userSeq = ? AND lectureSeq = ?", [result[0].userSeq, lectureSeq, result[0].userSeq, lectureSeq], function (err, result, next) {
                if (err) {
                    console.log(err);
                } else if (result.length == 0) {
                    var timestamp = new Date().getTime();
                    var params = {
                        userSeq: userSeq,
                        lectureSeq: lectureSeq,
                        time: timestamp,
                        //schoolname : result[0].shoolSeq
                    }
                    connection.query("insert into Recognition set ?", params, function (error, results, nexts) {
                        if (error) {
                            console.log(error);
                        } else {
                            console.log("insert success");
                            res.status(200).send("신청 완료");
                        }
                    })
                } else {
                    console.log("이미 신청되있음");
                    res.status(400).send("이미 신청이 되어있습니다.");
                }
            })
        }
        /*var reqSeq = result[0].reqLectureSeqs.indexOf('/'+lectureSeq+'/');
        if(reqSeq==-1){

            var lecList = result[0].reqLectureSeqs.split('/');
            lecList.pop();
            lecList.push(lectureSeq);
            lecList.push('0');
            var List = lecList.join('/');
            connection.query("update UserInfo set reqLectureSeqs = ? where userSeq = ?", [List, result[0].userSeq], function(error, results, nexts){
                if(error){
                    console.log(error);
                }else{
                    console.log("update success");
                }
            })

            var timestamp = new Date().getTime();
            var params = {
                userSeq : result[0].userSeq,
                lectureSeq : lectureSeq,
                time : timestamp,
                schoolname : result[0].shoolSeq
            }
            connection.query("insert into Recognition set ?", params, function(error, results, nexts){
                if(error){
                    console.log(error);
                }else{
                    console.log("insert success");
                    res.status(200).send("신청 완료");
                }
            })
        }else{
            console.log("이미 신청되있음");
            res.status(400).send("이미 신청이 되어있습니다.");
        }

    }
    */
    })
}

module.exports = router;
