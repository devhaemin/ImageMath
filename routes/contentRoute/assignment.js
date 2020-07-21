var express = require('express');
var router = express.Router();

const cUtil = require('../../customUtil');
const mysql = require('mysql');
const multer = require('multer');
const multerS3 = require('multer-s3');
const checkAuth = cUtil.checkAuth;

var AWS = require('aws-sdk');
var fs = require('fs');
AWS.config.loadFromPath("./config.json");
var s3 = new AWS.S3();

const connection = cUtil.connection;

const upload = multer({
    storage: multerS3({
        s3: s3,
        bucket: 'imagemath',
        ContentType: multerS3.AUTO_CONTENT_TYPE,
        key: (req, file, cb) => {
            console.log(file);
            var str = file.originalname;
            var res = str.substring(str.length - 5, str.length);
            cb(null, Date.now() + "_" + res);
        },
        acl: 'public-read',
    }),
    limits: {fileSize: 10 * 1024 * 1024},
});

function connectionQuery(connection, sql, params) {
    return new Promise((resolve, reject) => {
        if (params.length == 0) {
            connection.query(sql, function (error, results, next) {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        } else {
            connection.query(sql, params, function (error, results, next) {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            })
        }
    });
}

/**
 * @api {get} / 해당수업 과제 리스트 출력
 * @apiName assignment
 * @apiGroup assignment
 *
 */
router.get('/', assignment);//해당수업 과제 리스트 출력

router.post('/add', assignmentadd);

router.post('/add/answerFile/:assignmentSeq', upload.single('file'), assignmentAddAnswerFile);

router.get('/student', assignmentStudent);

router.get('/:assignmentSeq', assignmentSeq);//특정 과제 출력

router.get('/student/:assignmentSeq', studentAssignmentInfo);

router.get('/tutor/:assignmentSeq', tutorAssignmentInfo); // 튜터 전용, 한 과제당 총 인원 +

router.get('/tutor/send/all', tutorAssignmentInfoAll); // 튜터 전용, 한 과제당 총 인원 +

router.get('/tutor/submit/:assignmentSeq', getSubmitFiles);

router.patch('/assignmentAdm/:assignmentAdmSeq', patchSubmitState);

router.get('/tutor/sub/byStudent', getStudentSubmitAssignmentList);

router.post('/edit', editAssignmentInfo);

router.delete('/delete/answerFile/:fileSeq', deleteAnswerFile);

router.delete('/:assignmentSeq', deleteAssignmentInfo);

function deleteAssignmentInfo(req, res) {
    var token = req.headers['x-access-token'];
    var assignmentSeq = req.params.assignmentSeq;

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, userInfos) {
        if (err) {
            console.log(err);
            res.status(400).send();
        } else if (userInfos.length == 0 || userInfos[0].userType == "student") {
            res.status(400).send("잘못된 토큰입니다.");
        } else {
            connection.query("delete from AssignmentInfo where assignmentSeq = ?", assignmentSeq, function (err, result) {
                if (err) {
                    res.status(400).send({})
                } else {
                    res.status(200).send({})
                }
            })
        }
    });
}

function deleteAnswerFile(req, res) {

    var token = req.headers['x-access-token'];
    var fileSeq = req.params.fileSeq;
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
            connection.query("delete from FileInfo where fileSeq = ?", fileSeq, function (err, assignmentList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error");
                } else {
                    console.log("Delete File : " + fileSeq);
                    res.status(200).send({});
                }
            });
        }
    });
}

function getStudentSubmitAssignmentList(req, res) {
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
            connection.query("select * from AssignmentAdm AS aa JOIN AssignmentInfo AS ai ON aa.assignmentSeq = ai.assignmentSeq and aa.userSeq = ? order by ai.endTime desc", userSeq, function (err, assignmentList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error");
                } else {
                    res.status(200).send(assignmentList);
                }
            });
        }
    });
}

function patchSubmitState(req, res) {
    var token = req.headers['x-access-token'];
    var submitState = req.body.submitState;
    var assignmentAdmSeq = req.params.assignmentAdmSeq;

    connection.query('select * from AssignmentAdm where assignmentAdmSeq = ?', assignmentAdmSeq, function (err, result2) {
        if (err) {
            console.log(err);
        } else {
            if (result2.length != 0)
                cUtil.sendPushMessage(result2[0].userSeq, "과제 채점이 완료되었습니다.", "채점 결과를 확인해주세요.");
        }
    })

    connection.query('update AssignmentAdm set submitState = ? where assignmentAdmSeq = ?', [submitState, assignmentAdmSeq], function (err, result) {
        if (err) {
            console.log(err);
            res.status(400).send("FAIL");
        } else {
            res.status(200).send("SUCCESS");
        }
    })

}

function getSubmitFiles(req, res) {
    var token = req.headers['x-access-token'];

    var sql = 'select * from UserInfo where accessToken = ?';
    var params = [token];
    var userSeq = req.query.userSeq;
    var assignmentSeq = req.params.assignmentSeq;

    connectionQuery(connection, sql, params).then(
        response => {
            console.log(response);
            if (response.length === 0) {
                return Promise.resolve(-1);
            } else if (response[0].userType == 'tutor') {

                var sql = 'select * from AssignmentAdm order by userSeq';
                var params = [];
                return connectionQuery(connection, sql, params);
            } else {
                return Promise.resolve(-1);
            }
        },
    ).then(
        response => {
            console.log(response);
            if (response == -1) {
                return Promise.reject("엑세스 토큰이 만료되었습니다.");
            } else if (response.length == 0) {
                return Promise.resolve([]);
            } else {
                console.log(userSeq + "/" + assignmentSeq);

                var sql = 'select * from FileInfo where boardType = ? and userSeq = ? and postSeq = ? order by uploadTime desc';
                var params = ['assignment-submit-image', userSeq, assignmentSeq];
                return connectionQuery(connection, sql, params);

            }
        },
    ).then(
        response => {
            console.log(response);
            if (response.length == 0) {
                res.status(200).send(null);
            } else {
                res.status(200).send(response);
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );

}

function tutorAssignmentInfoAll(req, res) {

    var ret = [];

    var token = req.headers['x-access-token'];

    var sql = 'select * from UserInfo where accessToken = ?'
    var params = [token];
    connectionQuery(connection, sql, params).then(
        response => {
            console.log(response);
            if (response.length == 0) {
                return Promise.resolve(-1);
            } else if (response[0].userType == 'tutor') {

                var sql = 'select * from AssignmentAdm order by userSeq';
                var params = [];
                return connectionQuery(connection, sql, params);
            } else {
                return Promise.resolve(-1);
            }
        },
    ).then(
        response => {
            console.log(response);
            if (response == -1) {
                return Promise.reject("엑세스 토큰이 만료되었습니다.");
            } else if (response.length == 0) {
                return Promise.resolve([]);
            } else {
                console.log(response); //

                var sql = 'select * from FileInfo where boardType = ? order by userSeq';
                var params = ['assignment-submit-image'];
                for (var i = 0; i < response.length; i++) {
                    ret.push(response[i]);
                    if (i == response.length - 1) {
                        return connectionQuery(connection, sql, params);
                    }
                }
            }
        },
    ).then(
        response => {
            console.log(response);
            if (response.length == 0) {
                res.status(200).send([]);
            } else {
                console.log(response);
                var a = 0;
                var submitFiles = [];
                for (var i = 0; i < ret.length; i++) {
                    ret[i].submitFiles = []
                    for (var j = 0; j < response.length; j++) {
                        if (ret[i].userSeq == response[j].userSeq) {
                            ret[i].submitFiles.push(response[j]);

                            if (i == ret.length - 1 || j == response.length - 1) {
                                res.status(200).send(ret);
                            }
                        }
                    }
                }

            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );

}


function tutorAssignmentInfo(req, res) {
    var ret = [];

    var token = req.headers['x-access-token'];
    var assignmentSeq = req.params.assignmentSeq;

    var sql = 'select * from UserInfo where accessToken = ?';
    var params = [token];
    connectionQuery(connection, sql, params).then(
        response => {
            if (response.length == 0) {
                return Promise.resolve(-1);
            } else if (response[0].userType == 'tutor') {

                var sql = 'select * from AssignmentAdm where assignmentSeq = ? order by userSeq';
                var params = [assignmentSeq];
                return connectionQuery(connection, sql, params);
            } else {
                return Promise.resolve(-1);
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    ).then(
        response => {
            if (response == -1) {
                console.log("엑세스 토큰이 만료되었습니다.")
                res.status(400).send("엑세스 토큰이 만료되었습니다.");
            } else if (response.length == 0) {
                return Promise.resolve([]);
            } else {
                console.log(response); //

                var sql = 'select * from FileInfo where postSeq = ? and boardType = ? order by userSeq';
                var params = [assignmentSeq, 'assignment-submit-image'];
                for (var i = 0; i < response.length; i++) {
                    ret.push(response[i]);
                    if (i == response.length - 1) {
                        return connectionQuery(connection, sql, params);
                    }
                }
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    ).then(
        response => {
            if (response.length == 0) {
                console.log(ret);
                res.status(200).send(ret);
            } else {
                console.log(response);
                var a = 0;
                var submitFiles = [];
                for (var i = 0; i < ret.length; i++) {
                    ret[i].submitFiles = [];
                    for (var j = 0; j < response.length; j++) {
                        if (ret[i].userSeq == response[j].userSeq) {
                            ret[i].submitFiles.push(response[j]);
                            if (i == ret.length - 1 || j == response.length - 1) {
                                res.status(200).send(ret);
                                console.log(ret);
                            }
                        }
                    }
                }

            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );

}

//토큰이 일치하지 않으면 파일을 도로 삭제하는 기능을 추가해야함.

router.post('/submit/:assignmentSeq', upload.single('submitFile'), (req, res) => {
    const token = req.headers['x-access-token'];

    console.log(token);
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
            if (err) {
                console.log(err);
                res.status(400).send('token error');
            } else if (result.length == 0) {
                console.log("토큰에 맞는 유저 정보가 없습니다.");
                res.status(400).send('token error');
            } else {
                console.log(req.file);
                var assignmentSeq = req.params.assignmentSeq;
                var userSeq = result[0].userSeq;
                var bucket = 'imagemath/' + 'assingment/' + assignmentSeq + '/' + userSeq;
                var fileBucket = 'imagemath'
                var fileName = req.file.key;
                var fileType = 'image';
                var fileUrl = req.file.location;


                var values = {
                    'boardType': 'assignment-submit-image',
                    'postSeq': assignmentSeq,
                    'bucket': fileBucket,
                    'fileUrl': fileUrl,
                    'fileName': fileName,
                    'fileType': fileType,
                    'uploadTime': new Date().getTime(),
                    'userSeq': userSeq
                };
                connection.query('insert into FileInfo set ?', values, function (err, result, next) {
                    if (err) {
                        console.log(err);
                        res.status(400).send("파일 정보 입력 에러: " + err);

                        // submitstate를 0에서 1로 변경 UserInfo
                    } else {
                        var sql = 'select * from AssignmentAdm where userSeq = ?';
                        var params = [userSeq];
                        connectionQuery(connection, sql, params).then(
                            response => {
                                if (response[0].submitState == 0) {
                                    var sql = 'update AssignmentInfo set submitNum = submitNum + 1 where assignmentSeq = ?'
                                    var params = [assignmentSeq];

                                    return connectionQuery(connection, sql, params);
                                } else {
                                    return Promise.resolve();
                                }
                            },
                            error => {
                                console.log(error);
                                res.status(400).send(error);
                            }
                        ).then(
                            response => {
                                var sql = 'update AssignmentAdm set submitState = 1 where assignmentSeq = ? and userSeq = ?'
                                var params = [assignmentSeq, userSeq];

                                return connectionQuery(connection, sql, params);

                            },
                            error => {
                                console.log(error);
                                res.status(400).send(error);
                            }
                        ).then(
                            response => {
                                res.status(200).send("파일 정보 입력 완료");

                            },
                            error => {
                                console.log(error);
                                res.status(400).send(error);
                            }
                        );
                    }
                });
            }
        });
    }

});

function studentAssignmentInfo(req, res) {
    const assignmentSeq = req.params.assignmentSeq;
    const token = req.headers['x-access-token'];
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');

    } else {

        connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
            if (err) {
                console.log(err);
                res.status(400).send('token error');
            } else {
                var userSeq = result[0].studentCode;
                const sql = "select * from AssignmentAdm AS ad JOIN AssignmentInfo AS ai where ad.studentCode = ? and ad.assignmentSeq = ? and ad.assignmentSeq = ai.assignmentSeq"; //
                const sql3 = "select * from FileInfo where boardType = ? and userSeq = ? and postSeq = " + assignmentSeq + " order by uploadTime desc";  // submitFiles
                connection.query(sql, [userSeq, assignmentSeq], function (err, result, next) {
                    console.log(sql);
                    if (err) {
                        console.log(err);
                        res.status(400).send(err);
                    } else {
                        var ret = {};
                        ret = Object.assign(ret, result[0]);
                        connection.query(sql3, ['assignment-submit-image', userSeq], function (err, result3, next) {
                            console.log(sql3);
                            if (err) {
                                console.log(err);
                                res.status(400).send(err);
                            } else {
                                var temp = [];
                                var cnt = 0;

                                for (var i = 0; i < result3.length; i++) {
                                    temp.push(result3[i]);
                                    console.log(cnt, result3.length);
                                    if (cnt == result3.length - 1) {
                                        ret = Object.assign(ret, {submitFiles: temp});
                                        console.log(ret);
                                        res.status(200).send(ret);
                                    }
                                    cnt++;
                                }

                                if (result3.length == 0) {
                                    ret = Object.assign(ret, {submitFiles: temp});
                                    console.log(ret);
                                    res.status(200).send(ret);
                                }
                            }
                        });

                    }
                });
            }
        })
    }
}

function assignmentSeq(req, res) {
    const assignmentSeq = req.params.assignmentSeq;
    console.log(assignmentSeq);
    const sql = "select * from AssignmentInfo where assignmentSeq = ? order by endTime desc";
    var sql1 = mysql.format(sql, assignmentSeq);
    connection.query(sql1, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("assignmentSeq error");
        } else {
            console.log("assignmentSeq query");
            connection.query("select * from FileInfo where postSeq = ? and boardType = ?", [assignmentSeq, "assignment-answer"], function (err, answerFilesSql) {
                if (err) {
                    console.log(err);
                    res.status(400).send("error");
                } else if (answerFilesSql.length == 0) {
                    res.status(200).send(result[0]);
                } else {
                    var ret = result[0];
                    ret.answerFiles = answerFilesSql;
                    console.log(ret);
                    res.status(200).send(ret);
                }
            })
        }
    })
}

function assignment(req, res) {
    const token = req.headers['x-access-token'];
    var page = req.query.page;
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        const sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        var pageData = [];
        const sql2 = "select * from AssignmentInfo order by endTime desc";
        // var sql3 = mysql.format(sql2, lectureSeq);
        connection.query(sql1, function (err, result, next) {
            if (err) {
                console.log(err);
                res.status(400).send("token error");
            } else {
                connection.query(sql2, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                        res.status(400).send("assignment query error");
                    } else {
                        console.log("assignmentList get");
                        /*
                        for(var i = 0; i < 20 && i <= results.length;i++){
                            pageData[i] = results[page * 20 - 20 + i];
                        }*/
                        res.status(200).send(results);
                    }
                })
            }
        })
    }

}

function editAssignmentInfo(req, res) {
    const token = req.headers['x-access-token'];
    var reqbody = {
        title: req.body.title,
        postTime: req.body.postTime,
        endTime: req.body.endTime,
        lectureTime: req.body.lectureTime,
        lectureSeq: req.body.lectureSeq,
        lectureName: req.body.lectureName,
        contents: req.body.contents
    };
    var assignmentSeq = req.body.assignmentSeq;
    reqbody.postTime = Date.now();
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        const sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        connection.query(sql1, function (err, result, next) {
            if (err || result == undefined) {
                console.log("token error");
                res.status(400).send("token error");
            } else if (result[0].userType == "tutor") {
                connection.query("update AssignmentInfo set ? where assignmentSeq = ?", [reqbody, assignmentSeq], function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                    } else {

                        res.status(200).send(req.body);
                    }
                })
                //var content;
                //cUtil.fcmpush(token, content, userSeq, lectureSeq);
            } else {
                console.log("permission denied");
                res.status(400).send("권한이 없습니다.");
            }
        })
    }
}

function assignmentadd(req, res) {
    const token = req.headers['x-access-token'];

    var reqbody = {
        title: req.body.title,
        postTime: req.body.postTime,
        endTime: req.body.endTime,
        lectureTime: req.body.lectureTime,
        lectureSeq: req.body.lectureSeq,
        lectureName: req.body.lectureName,
        contents: req.body.contents
    };
    reqbody.postTime = Date.now();
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        const sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        connection.query(sql1, function (err, result, next) {
            if (err) {
                console.log("token error");
                res.status(400).send("token error");
            } else if (result[0].userType == "tutor") {
                connection.query("insert into AssignmentInfo set ?", reqbody, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                    } else {
                        //    var lectureSeqs = "%/" + reqbody.lectureSeq + "/%";
                        connection.query("select * from LectureAdm where lectureSeq = ?", reqbody.lectureSeq, function (err2, result2, next2) {
                            if (err2) {
                                console.log(err2);
                            } else {
                                for (var i = 0; i < result2.length; i++) {
                                    connection.query("insert into AssignmentAdm (userSeq, studentCode, assignmentSeq, uploadTime) values (?, ?, ?, ?)", [result2[i].userSeq, result2[i].studentCode, results.insertId, reqbody.postTime], function (err3, result3, next3) {
                                        if (err3) {
                                            console.log(err3);
                                        } else {
                                            console.log("insert AssignmentAdm");
                                        }
                                    })
                                }
                                reqbody.assignmentSeq = results.insertId
                                connection.query("update AssignmentInfo set studentNum = ? where assignmentSeq = ?", [result2.length, reqbody.assignmentSeq], function (err, resq) {
                                    if (err) {
                                        console.log(err);
                                    }
                                });
                                res.status(200).send(reqbody);
                            }
                        })
                    }
                })
                //var content;
                //cUtil.fcmpush(token, content, userSeq, lectureSeq);
            } else {
                console.log("permission denied");
                res.status(400).send("권한이 없습니다.");
            }
        })
    }
}

function assignmentAddAnswerFile(req, res) {
    var token = req.headers['x-access-token'];
    var assignmentSeq = req.params.assignmentSeq;
    var file = req.file;

    var sql = 'select * from UserInfo where accessToken = ?'
    var params = [token];
    connectionQuery(connection, sql, params).then(
        response => {
            if (response.length == 0) {
                return Promise.resolve(-1);
            } else if (response[0].userType == 'tutor') {

                var sql = 'insert into FileInfo set ?';
                var params = [{
                    boardType: 'assignment-answer',
                    postSeq: assignmentSeq,
                    bucket: 'imagemath',
                    fileUrl: file.location,
                    fileName: file.key,
                    fileType: 'normal',
                    uploadTime: Math.floor(new Date().getTime() / 1000),
                    userSeq: response[0].userSeq
                }];
                return connectionQuery(connection, sql, params);
            } else {
                return Promise.resolve(-1);
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    ).then(
        response => {
            if (response == -1) {
                console.log("엑세스 토큰이 만료되었습니다.")
                res.status(400).send("엑세스 토큰이 만료되었습니다.");
            } else {
                console.log("uploadAssignmentAnswer done")
                res.status(200).send("uploadAssignment: 파일 업로드 및 파일 정보 입력이 완료되었습니다.");
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );
}

function assignmentStudent(req, res) {
    console.log('assignmentStudent');
    const token = req.headers['x-access-token'];
    var page = req.query.page;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log("token error");
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length == 0) {
            console.log("token expired");
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            connection.query("select * from AssignmentAdm AS ad JOIN AssignmentInfo AS ai where ad.assignmentSeq = ai.assignmentSeq and ad.studentCode = ? order by ai.endTime desc", result[0].studentCode, function (err1, result1, next1) {
                if (err1) {
                    console.log(err1)
                } else if (result1.length == 0) {
                    res.status(200).send([]);
                } else {
                    res.status(200).send(result1);
                }
            })
        }
    })
}

module.exports = router;
