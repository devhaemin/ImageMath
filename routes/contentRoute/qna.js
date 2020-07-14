var express = require('express');
var router = express.Router();
const cUtil = require('../../customUtil');
const multer = require('multer');
const storage = multer.memoryStorage();
const upload = multer({storage});
var AWS = require('aws-sdk');
var fs = require('fs');
AWS.config.region = 'ap-northeast-2';

const multerS3 = require('multer-s3');
AWS.config.loadFromPath("./config.json");
var s3 = new AWS.S3();
const mysql = require('mysql');
const connection = cUtil.connection;

const questionUpload = multer({
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
const answerUpload = multer({
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


router.post('/editQuestion/:questionSeq', questionUpload.single('file'), editQuestion);
router.post('/question', addQuestion);

router.post('/editAnswer/:answerSeq', answerUpload.single('file'), editAnswer);
router.post('/answer', addAnswer);

router.get('/question', getQuestionList);
router.get('/question/file/:questionSeq', getQuestionFileList);
router.get('/answer/:questionSeq', getAnswerList);
router.get('/answer/file/:answerSeq', getAnswerFileList);

const answer_board_type = "answer-submit-files";


function getAnswerList(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, userInfo) {
        if (err || userInfo.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            connection.query("select * from AnswerList where questionSeq = ? order by postTime desc", req.params.questionSeq,
                function (err, answerList) {
                    if (err) {
                        console.log(err);
                        res.status(400).send("SQL Error!");
                    } else {
                        res.status(200).send(answerList);
                    }
                }
            );
        }
    });
}

function getAnswerFileList(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, userInfo) {
        if (err || userInfo.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            connection.query("select * from FileInfo where boardType = ? and postSeq = ?", [answer_board_type, req.params.answerSeq],
                function (err, fileList) {
                    if (err) {
                        console.log(err);
                        res.status(400).send("SQL error!");
                    } else {
                        res.status(200).send(fileList);
                    }

                });
        }
    });
}

function getQuestionList(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result.length != 0 && result[0].userType != "tutor") {
            const userSeq = result[0].userSeq;
            connection.query("select * from QuestionList AS ql JOIN UserInfo AS ui where ql.uploaderSeq = ? and ql.uploaderSeq = ui.userSeq order by updateTime desc, postTime desc", userSeq, function (err, questionList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error!");
                } else {
                    res.status(200).send(questionList);
                }
            });
        } else {
            connection.query("select * from QuestionList AS ql JOIN UserInfo AS ui where ql.uploaderSeq = ui.userSeq order by postTime desc", function (err, questionList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error!");
                } else {
                    res.status(200).send(questionList);
                }
            });
        }
    });
}

function getQuestionFileList(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, userInfo) {
        if (err || userInfo.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            const userSeq = userInfo[0].userSeq;
            connection.query("select * from FileInfo where boardType = ? and postSeq = ?", ["question-submit-files", req.params.questionSeq], function (err, questionFiles) {
                if (err) {
                    console.log(err);
                    res.status(400).send("질문파일 SQL 에러");
                } else {
                    res.status(200).send(questionFiles);
                }
            })
        }
    });
}


function addQuestion(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            var params = {
                uploaderSeq: result[0].userSeq,
                title: req.body.title,
                contents: req.body.contents,
                postTime: Date.now(),
                updateTime: 0
            }
            connection.query("INSERT INTO QuestionList SET ?", params, function (err, result) {
                if (err) {
                    console.log(err);
                    res.status(400).send("Insert Error!");
                } else {
                    console.log(result);
                    var question = {
                        questionSeq: result.insertId
                    }
                    res.status(200).send(question);

                }
            });

        }
    });
}

function editQuestion(req, res) {
    console.log("editQuestion");
    const token = req.headers['x-access-token'];
    var questionSeq = req.params.questionSeq;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            console.log(result);
            var sql = 'insert into FileInfo set ?';
            var params = [{
                boardType: 'question-submit-files',
                postSeq: questionSeq,
                bucket: 'imagemath',
                fileUrl: req.file.location,
                fileName: req.file.key,
                fileType: 'normal',
                uploadTime: new Date().getTime(),
                userSeq: result[0].userSeq
            }];
            connection.query(sql, params, function (err, re) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error!");
                } else {
                    res.status(200).send("Success!");
                }
            });
        }
    })
}

function addAnswer(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            var params = {
                uploaderSeq: result[0].userSeq,
                title: req.body.title,
                contents: req.body.contents,
                postTime: Date.now(),
                updateTime: Date.now(),
                questionSeq: req.body.questionSeq
            }
            connection.query("INSERT INTO AnswerList SET ?", params, function (err, result) {
                if (err) {
                    console.log(err);
                    res.status(400).send("Insert Error!");
                } else {
                    console.log(result);
                    var answer = {
                        answerSeq: result.insertId
                    }
                    res.status(200).send(answer);
                }
            });

            connection.query("UPDATE QuestionList SET updateTime = ? WHERE questionSeq = ?", [Date.now(), req.body.questionSeq], function (err, results) {
                if (err) {
                    console.log(err);
                }
            });

            connection.query("SELECT uploaderSeq from QuestionList where questionSeq = ?", params.questionSeq, function (err, studentInfo) {
                if (err) {
                    console.log(err);
                } else {
                    cUtil.sendPushMessage(studentInfo[0].uploaderSeq, "답변이 등록되었습니다.", '"' + params.title + '"' + " 에 대한 답변이 등록됐습니다.");
                }
            });

        }
    });
}

function editAnswer(req, res) {
    console.log("editAnswer");
    const token = req.headers['x-access-token'];
    var answerSeq = req.params.answerSeq;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err || result.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            console.log(result);
            var sql = 'insert into FileInfo set ?';
            var params = [{
                boardType: answer_board_type,
                postSeq: answerSeq,
                bucket: 'imagemath',
                fileUrl: req.file.location,
                fileName: req.file.key,
                fileType: 'normal',
                uploadTime: new Date().getTime(),
                userSeq: result[0].userSeq
            }];
            connection.query(sql, params, function (err, re) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL Error!");
                } else {
                    res.status(200).send("Success!");
                }
            });
        }
    })
}


module.exports = router;
