var express = require('express');
var router = express.Router();
const cUtil = require('../../customUtil');
const mysql = require('mysql');
const XLSX = require('xlsx');
const multer = require('multer');
const storage = multer.memoryStorage();
const upload = multer({storage});
const connection = cUtil.connection;

const multerS3 = require('multer-s3');
var AWS = require('aws-sdk');
var s3 = new AWS.S3();
const testUpload = multer({
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
        if (params.length === 0) {
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

router.post('/', upload.single('file'), testtest);
router.get('/student/:lectureSeq', studentTest); // 한 강의에 대한 학생 하나의 모든 테스트 기록
router.get('/student', testInfo); // 한 학생에 대해서 모든 테스트 결과
router.get('/tutor/:lectureSeq', tutorTest);
router.get('/tutor', getTestInfo);
router.get('/result', testresult); // 한 테스트에 대해서 모든 학생들의 테스트 결과
router.post('/add', addTestInfo);
router.post('/answer/:testSeq', testUpload.single('file'), uploadAnswerFile);
router.post('/tutor/uploadXls/:testSeq', testUpload.single('file'), uploadXls);

router.delete('/:testSeq', deleteTestInfo);

function deleteTestInfo(req, res) {
    var token = req.headers['x-access-token'];
    var testSeq = req.params.testSeq;

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, userInfos) {
        if (err) {
            console.log(err);
            res.status(400).send();
        } else if (userInfos.length == 0 || userInfos[0].userType == "student") {
            res.status(400).send("잘못된 토큰입니다.");
        } else {
            connection.query("delete from TestInfo where testSeq = ?", testSeq, function (err, result) {
                if (err) {
                    res.status(400).send({})
                } else {
                    res.status(200).send({})
                }
            })
        }
    });
}

function addTestInfo(req, res) {
    var token = req.headers['x-access-token'];
    var title = req.body.title;
    var postTime = req.body.postTime;
    var endTime = req.body.endTime;
    var lectureTime = req.body.lectureTime;
    var lectureSeq = req.body.lectureSeq;
    var lectureName = req.body.lectureName;
    var contents = req.body.contents;
    console.log("Add TestInfo");

    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log(err);
        } else if (result.length == 0) {
            res.status(400).send();
        } else if (result[0].userType = "tutor") {
            sql = "INSERT INTO TestInfo SET ?"
            var param = {
                title: title,
                postTime: postTime,
                endTime: endTime,
                lectureTime: lectureTime,
                lectureSeq: lectureSeq,
                lectureName: lectureName,
                contents: contents
            }

            connection.query(sql, param, function (err, results, field) {
                if (err) {
                    console.error(err);
                    res.status(500).send('500 SERVER ERROR');
                } else {
                    console.log(results);
                    param.testSeq = results.insertId;
                    res.status(200).send(param);
                }
            })
        } else {
            console.log("permission error");
            res.status(400).send("권한이 없습니다");
        }
    })

}

function uploadAnswerFile(req, res) {
    var token = req.headers['x-access-token'];
    var testSeq = req.params.testSeq;
    var file = req.file;

    var sql = 'select * from UserInfo where accessToken = ?'
    var params = [token];
    connectionQuery(connection, sql, params).then(
        response => {
            if (response.length == 0) {
                return Promise.resolve(-1);
            } else if (response[0].userType == 'tutor') {
                console.log(file);
                var sql = 'insert into FileInfo set ?';
                var params = [{
                    boardType: 'test-submit-answer',
                    postSeq: testSeq,
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
                console.log("uploadAnswerFiles done")
                res.status(200).send("uploadAnswerFile: 파일 업로드 및 파일 정보 입력이 완료되었습니다.");
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );
}

function uploadXls(req, res) {
    var token = req.headers['x-access-token'];
    var testSeq = req.params.testSeq;
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
                    boardType: 'test-submit-xls',
                    postSeq: testSeq,
                    bucket: 'imagemath',
                    fileUrl: file.location,
                    fileName: file.key,
                    fileType: 'xls',
                    uploadTime: new Date().getTime(),
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
                var AWS = require('aws-sdk');
                AWS.config.region = 'ap-northeast-2';
                var s3 = new AWS.S3();
                var fileS3 = require('fs').createWriteStream('logo.xlsx');
                var params = {Bucket: 'imagemath', Key: req.file.key};
                var readStream = s3.getObject(params).createReadStream().pipe(fileS3);
                readStream.on('finish', function () {
                    testtest("logo.xlsx", req, res);
                });
            }
        },
        error => {
            console.log(error);
            res.status(400).send(error);
        }
    );
}

function testtest(file, req, res) {
    let workbook = XLSX.readFile(file);
    let sheet = workbook.SheetNames[0];
    let worksheet = workbook.Sheets[sheet];
    var xlsLength = req.body.xlsLength;
    var cnt = 4;
    for (var i = 4; i < xlsLength - 1; i++) {
        var studentCode = worksheet["D" + i];
        var userName = worksheet["C" + i];
        var score = worksheet["F" + i];
        var rank = worksheet["A" + i];
        var averageScore = worksheet["F" + (xlsLength - 1)];
        if (studentCode == undefined || userName == undefined || score == undefined || rank == undefined || averageScore == undefined) {
            res.status(400).send("NOT EXCEL!");
            console.log("적합하지 않은 액셀 형식입니다.");
            return;
        }

        var lectureSeq = req.body.lectureSeq.replace(/\"/gi, "");

        var params = {
            submitState: 1,
            submitFileUrl: "",
            uploadTime: new Date().getTime(),
            testSeq: req.params.testSeq,
            lectureSeq: lectureSeq,
            studentCode: studentCode.w.replace(" ", ""),
            userName: userName.w,
            score: Math.floor(score.w),
            rank: rank.w
        }
        connection.query("insert into TestAdm SET ? ", params, function (err, insertResult) {
            if (err) {
                console.log(err);
                cnt++;
                if (cnt == xlsLength - 2) {
                    console.log("ERROR! db")
                    res.status(400).send("Error!");
                }
            } else {

                connection.query("select ui.userSeq, li.name from UserInfo AS ui JOIN LectureInfo AS li where ui.studentCode = ? and li.lectureSeq = ?", [params.studentCode, params.lectureSeq], function (err, studentInfo) {
                    if (err) {
                        console.log(err);
                    } else if (studentInfo.length != 0) {
                        cUtil.sendPushMessage(studentInfo[0].userSeq, "테스트 결과가 등록되었습니다.", '테스트 메뉴에서 "' + studentInfo[0].name + '"' + "  수업을 선택해 확인해보세요.")
                    }
                })

                cnt++;
                if (cnt == xlsLength - 2) {
                    connection.query("update TestInfo SET studentNum = ? ,averageScore = ? where testSeq = ?",
                        [cnt, Math.floor(averageScore.w), req.params.testSeq], function (err, results) {
                            if (err) {
                                console.log(err);
                                res.status(400).send("Error!");
                            } else {
                                console.log("SUCCESS UPLOAD ALL EXCELS");
                                res.status(200).send("Good!");
                            }
                        });
                }
            }
        });
    }
}

/**
 * @api {get} test/student/:lectureSeq GetTestList by Lecture
 * @apiName Get Test list group by lecture
 * @apiGroup Test
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 */

function studentTest(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;
    if (!userInfo) {
        res.status(400).send("token error");
    } else if (userInfo.userType === "student") {
        const sql2 = "select * from TestAdm AS ta JOIN TestInfo AS ti where ta.studentCode = ? and ta.lectureSeq = ? and ta.testSeq = ti.testSeq order by lectureTime";
        const sql3 = mysql.format(sql2, [userInfo.studentCode, lectureSeq]);
        connection.query(sql3, function (error, results) {
            if (error) {
                console.log(error);
                res.status(400).send("search error");
            } else {
                console.log("search test by lecture");
                res.status(200).send(results);
            }
        })
    }
}

/**
 * @api {get} test/student GetTestDetail for Student
 * @apiName Get Test detail for student
 * @apiGroup Test
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission student
 * @apiParam {Int} testAdmSeq
 */

function testInfo(req, res) {
    const userInfo = req.userInfo;
    const testAdmSeq = req.query.testAdmSeq;
    if (!userInfo) {
        res.status(500).send("token error");
    } else if (userInfo.userType === "student") {
        const sql2 = "select * from TestAdm AS ta JOIN TestInfo AS ti where ta.testAdmSeq = ? and ti.testSeq = ta.testSeq order by lectureTime";
        connection.query(sql2, testAdmSeq, function (error, results) {
            if (error) {
                console.log(error);
                res.status(400).send("Seq error");
            } else {
                console.log("testAdm search");
                res.status(200).send(results[0]);
            }
        })
    } else {
        console.log("permission error");
        res.status(400).send("Permission error");
    }

}

function tutorTest(req, res) {
    const token = req.headers['x-access-token'];
    const lectureSeq = req.params.lectureSeq;
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        var sql = "select * from UserInfo where accessToken = ? ";
        var sql1 = mysql.format(sql, token);
        connection.query(sql1, function (err, result) {
            if (err) {
                console.log(err);
                res.status(500).send("token error");
            } else if (result.length === 0) {
                res.status(400).send("token error!");
            } else if (result[0].userType === "tutor") {
                var sql2 = "select * from TestInfo where lectureSeq = ?  order by testSeq DESC";
                var sql3 = mysql.format(sql2, lectureSeq);
                connection.query(sql3, function (error, results) {
                    if (error) {
                        console.log(error);
                        res.status(500).send("lectureSeq error");
                    } else {
                        console.log("testList by lectureSeq");
                        res.status(200).send(results);
                    }
                })
            } else {
                console.log("permission error");
                res.status(400).send("permission error");
            }

        })
    }
}

function getTestInfo(req, res) {
    const token = req.headers['x-access-token'];
    var testSeq = req.query.testSeq;
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        var sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        connection.query(sql1, function (err, userInfos, next) {
            if (err) {
                console.log(err);
                res.status(500).send("token error");
            } else {
                var sql2 = "select * from TestInfo where testSeq = ?";
                var sql3 = mysql.format(sql2, testSeq);
                connection.query(sql3, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                        res.status(500).send("testSeq error");
                    } else {
                        console.log("testInfo postSeq = " + testSeq);
                        connection.query("select * from FileInfo where postSeq = ? and boardType = ?", [testSeq, "test-submit-answer"], function (err, answerFilesSql) {
                            if (err) {
                                console.log(err);
                                res.status(400).send("error");
                            } else if (answerFilesSql.length == 0) {
                                res.status(200).send(results[0]);
                            } else {
                                var ret = results[0];
                                ret.answerFiles = answerFilesSql;
                                console.log(ret);
                                res.status(200).send(ret);
                            }
                        });
                    }
                })
            }
        })
    }
}

function testresult(req, res) {
    const token = req.headers['x-access-token'];
    var testSeq = req.query.testSeq;
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        var sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        connection.query(sql1, function (err, result, next) {
            if (err) {
                console.log(err);
                res.status(500).send("token error");
            } else if (result[0].userType === "tutor") {
                var sql2 = "select ta.score,ta.rank,ti.averageScore,ta.studentCode, ta.userName, ti.testSeq, ti.title, ti.postTime, ti.endTime, ti.lectureTime, ti.studentNum, ti.contents, ti.lectureName from TestAdm AS ta JOIN TestInfo AS ti where ta.testSeq = ? and ta.testSeq = ti.testSeq order by score desc"
                var sql3 = mysql.format(sql2, testSeq);
                connection.query(sql3, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                        res.status(400).send("testSeq error");
                    } else {
                        console.log("test result tutor");
                        res.status(200).send(results);
                    }
                })
            } else {
                var sql2 = "select ta.score,ta.rank,ti.averageScore,ta.studentCode, ta.userName, ti.testSeq, ti.title, ti.postTime, ti.endTime, ti.lectureTime, ti.studentNum, ti.contents, ti.lectureName from TestAdm AS ta JOIN TestInfo AS ti where ta.testSeq = ? and ta.testSeq = ti.testSeq order by score desc limit5";
                connection.query(sql2, testSeq, function (error, results) {
                    if (error) {
                        console.log(error);
                        res.status(500).send("testSeq error");
                    } else if (results.length == 0) {
                        res.status(200).send([]);
                    } else {
                        console.log("test result student");
                        res.status(200).send(results);
                    }
                })
            }
        })
    }
}
module.exports = router;
