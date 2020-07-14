var express = require('express');
var router = express.Router();
var admin = require('firebase-admin');
var FCM = require('fcm-node');
const cUtil = require('../../customUtil');
const mysql = require('mysql');

const connection = cUtil.connection;

router.get('/', alarmList);
router.get('/send', sendAlarm);
router.post('/pushToken', setPushToken);

function setPushToken(req, res) {
    const accessToken = req.headers['x-access-token'];
    connection.query("select userSeq from UserInfo where accessToken = ?", accessToken, function (err, userInfo) {
        if (err || userInfo.length == 0) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else {
            connection.query("update UserInfo SET fcmToken = ? where userSeq = ?", [req.body.fcmToken, userInfo[0].userSeq], function (err, result) {
                if (err) {
                    console.log(err);
                    res.status(400).send("Error!");
                } else {
                    res.status(200).send(result);
                }
            })
        }
    })
}

//알람 리스트 보내기
function alarmList(req, res) {
    const token = req.headers['x-access-token'];
    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    } else {
        const sql = "select * from UserInfo where accessToken = ?";
        var sql1 = mysql.format(sql, token);
        const sql2 = "select * from Alarm";
        connection.query(sql1, function (err, result, next) {
            if (err || result.length == 0) {
                console.log(err);
                res.status(400).send("토큰이 만료되었습니다.");
            } else {
                var userSeq = result[0].userSeq;
                var AlarmData = {};
                connection.query("select * from Alarm where userSeq = ? order by postTime desc", userSeq, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                        res.status(400).send("alarmList error");
                    } else {
                        console.log("Alarm List send");
                        res.status(200).send(results);
                    }
                })
            }
        })
    }
};

//알람 보내기
function sendAlarm(req, res) {
    var token = req.headers['x-access-token'];
    var lectureSeq = req.query.lectureSeq;
    var content = req.query.content;
    var sql = "select * from UserInfo where accessToken = ?";
    var sql1 = mysql.format(sql, token);
    var sql2 = "select * from UserInfo where userType = 'student' and lectureSeqs like ";
    var sql3 = sql2 + '"%/' + lectureSeq + '/%"';
    var sql4 = "select fcmToken from UserInfo where userSeq = ?";
    connection.query(sql1, function (err, result, next) {
        if (err) {
            console.log(err);
            res.status(400).send("토큰이 만료되었습니다.");
        } else if (result[0].userType == "tutor") {
            connection.query(sql3, function (error, results, nexts) {
                if (error) {
                    console.log(error);
                    res.status(400).send("다시 시도해주세요.");
                } else {
                    console.log("before send Alarm : " + results);
                    for (var i = 0; i < results.length; i++) {
                        console.log("알람 전송 : " + results[i].userSeq);
                        var push_data = {
                            to: results[i].fcmToken,
                            data: {
                                "title": "수업 알림",
                                "body": content
                            }
                        }
                        fcm.send(push_data, function (err, response) {
                            if (err) {
                                console.error('Push메시지 발송에 실패했습니다.');
                                console.error(err);
                            }
                            console.log('Push메시지가 발송되었습니다.');
                        });

                    }
                    setTimeout(() => {
                        console.log("전송 완료");
                        res.status(200).send("알람이 전송되었습니다.");
                    }, 3000);
                }
            })
        } else {
            console.log("permission error : " + result[0].userType);
            res.status(400).send("권한이 없습니다.");
        }
    })
}

module.exports = router;