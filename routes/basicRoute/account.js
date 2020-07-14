const express = require('express');
const router = express.Router();
const crypto = require('crypto');
const cUtil = require('../../customUtil');
const mysql = require('mysql');

const connection = cUtil.connection;
const secretKey1 = "stu";
const secretKey2 = "tut";

router.get('/school', school);
router.get('/academy', academy);
router.get('/reqlecture', getReqLecture);
router.post('/reqlecture', postReqLecture);
router.get('/:userSeq', getUserInfo);

function school(req, res) {
    connection.query("select * from SchoolList", function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("에러");
        } else {
            console.log("schoolList");
            res.status(200).send(result);
        }
    })
}

function academy(req, res) {
    console.log("academyList");
    connection.query("select * from AcademyList", function (err, result) {
        if (err) {
            console.log(err);
            res.status(400).send("에러");
        } else {
            console.log("acadmyList");
            res.status(200).send(result);
        }
    })
}

function getUserInfo(req, res) {
    var accessToken = req.headers['x-access-token'];
    var userSeq = req.params.userSeq;
    connection.query("select * from UserInfo where accessToken = ?", accessToken, function (err, requestUser) {
        if (err) {
            console.log(err);
            res.status(400).send("token error");
        } else if (requestUser.userType == "student") {
            console.log("student token");
            res.status(400).send("token error");
        } else {
            connection.query("select * from UserInfo where userSeq = ?", userSeq, function (err, userInfos) {
                if (err) {
                    console.log(err);
                    res.status(400).send("query error");
                } else {
                    res.status(200).send(userInfos[0]);
                }
            })
        }
    })
}

function getReqLecture(req, res) {
    const reqlecture = req.body.reqlecture;
    let sqls = "";

    if (serach == NULL) {
        sqls = "SELECT lectureSeq, lectureName, time, JSON_OBJECT('acadmeySeq', a.academySeq, 'academyName', a.acadmeyName ) from LectureInfo l JOIN acadmeyList a ON l.academySeq= a,academy";
    } else {
        const sql = "SELECT lectureSeq, lectureName, time, JSON_OBJECT('acadmeySeq', a.academySeq, 'academyName', a.acadmeyName) from LectureInfo l JOIN acadmeyList a ON l.academySeq = a, academy WHERE l.lectureName = % ? %";
        const param = reqlecture;
        sqls = mysql.format(sql, param);
    }
    connection.query(sqls, function (err, rows, field) {
        if (err) {
            console.error(err.stack);
            res.status(500).send('500 SERVER ERROR');
        } else {
            res.status(200).send(rows);
        }
    })
}

function postReqLecture(req, res) {
    if (secretKey != secretKey1) {
        res.status(400).send('TOKEN ERROR');
    } else {
        const reqlecture = req.body.reqLecture;
        const userSeq = req.decoded.userSeq;

        const sql1 = "UPDATE UserInfo (reqLectureSeqs) VALUES ? WHERE userSeq = ? AND WHERE reqLectureSeqs != % ? %";
        const param1 = [reqlecture, userSeq, reqlecture];
        const sqls1 = mysql.format(sql1, param1);

        const sql2 = "UPDATE LectureInfo reqStudentCnt VALUES ?  WHERE lectureSeq = ?"
        const param2 = [reqlecture, reqlecture];
        const sqls2 = mysql.format(sql2, param2)

        connection.query(sqls1 + sqls2, function (err, rows, field) {
            if (err) {
                console.error(err.stack);
                res.status(500).send('500 SERVER ERROR');
            } else if (rows[0].affectedRows != 1 && rows[1].affectedRows != 1) {
                console.error(err.stack);
                res.status(500).send('500 SERVER ERROR, db');
            } else {
                res.status(200);
            }
        })
    }
}


module.exports = router;
