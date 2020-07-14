const express = require('express');
const router = express.Router();
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
const notice_board_type = 'notice-submit-images';

const noticeUpload = multer({
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

router.post('/', addNotice);
router.post('/:noticeSeq', noticeUpload.single('file'), editNotice);
router.get('/', noticeList);
router.get('/file', getNoticeFile);
router.delete('/delete/:noticeSeq', noticeDelete);

function getNoticeFile(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            connection.query("select * from FileInfo where boardType = ? and postSeq = ?", [notice_board_type, req.query.noticeSeq],
                function (err, fileList) {

                    if (err) {
                        console.log(err);
                        res.status(400).send("File Query Error!");
                    } else {
                        res.status(200).send(fileList);
                    }
                });
        }
    })
}

function addNotice(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log("token error");
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result[0].userType == "tutor") {
            const lectureSeq = req.body.lectureSeq;
            const file = req.body.file;
            const title = req.body.title;
            const sql = "INSERT INTO NoticeInfo SET ?;";
            const param = {
                title: req.body.title,
                contents: req.body.contents,
                lectureSeq: lectureSeq,
                postTime: Date.now()
            };
            const sqls = mysql.format(sql, param);

            connection.query(sqls, function (err, rows, field) {
                if (err) {
                    console.error(err);
                    res.status(500).send('500 SERVER ERROR');
                } else {
                    console.log("notice upload");
                    connection.query("select * from NoticeInfo where noticeSeq = ?", rows.insertId, function (error, result2, next) {
                        if (error) {
                            console.log("noticeSeq error");
                            console.log(error);
                            res.status(400).send(error);

                        } else {

                            connection.query("select * from LectureAdm where lectureSeq = ?", lectureSeq, function (error, result) {
                                if (error) {

                                    console.log("noticeSeq error");
                                    console.log(error);
                                    res.status(400).send(error);

                                } else {
                                    res.status(200).send(result2[0]);
                                    for (var i = 0; i < result.length; i++) {
                                        cUtil.sendPushMessage(result[i].userSeq, title, '"' + req.body.title + '"' + " 공지가 등록되었습니다.");
                                    }
                                }
                            })
                        }
                    })
                }
            })
        } else {
            res.status(400).send("권한이 없습니다.");
        }
    })
}

function editNotice(req, res) {
    console.log("editNotice");
    const token = req.headers['x-access-token'];
    var noticeSeq = req.params.noticeSeq;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log("token error");
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result[0].userType == "tutor") {
            console.log(result);
            var sql = 'insert into FileInfo set ?';
            var params = [{
                boardType: notice_board_type,
                postSeq: noticeSeq,
                bucket: 'imagemath',
                fileUrl: req.file.location,
                fileName: req.file.key,
                fileType: 'image',
                uploadTime: new Date().getTime(),
                userSeq: result[0].userSeq
            }];
            connectionQuery(connection, sql, params).then(
                response => {

                    res.status(200).send("공지 이미지 업로드 완료");
                },
                error => {
                    res.status(400).send("권한이 없습니다.");
                }
            )
        } else {
            res.status(400).send("권한이 없습니다.");
        }
    })
}

function noticeList(req, res) {
    const token = req.headers['x-access-token'];
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log("token error");
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            var lectureSeq = req.query.lectureSeq;
            //console.log(lectureSeq);
            connection.query("select * from NoticeInfo where lectureSeq = ?", lectureSeq, function (err, result, next) {
                if (err) {
                    console.log(err);
                    res.status(400).send("공지를 불러오지 못했습니다.");
                } else {
                    var column = 0;
                    res.status(200).send(result);
                }
            })
        }
    })
}


function noticeDelete(req, res) {
    const token = req.headers['x-access-token'];
    const noticeSeq = req.params.noticeSeq;
    connection.query("select * from UserInfo where accessToken = ?", token, function (err, result, next) {
        if (err) {
            console.log("token error");
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            var sql = 'delete from NoticeInfo where noticeSeq = ?'
            var params = [noticeSeq];
            console.log(sql);
            connectionQuery(connection, sql, params).then(
                response => {
                    console.log(response);
                    res.status(200).send("삭제가 완료되었습니다.");
                },
                error => {
                    console.log(error);
                    res.status(400).send(error);
                }
            )
        }
    })
}


module.exports = router;
