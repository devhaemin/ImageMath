const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');
const mysql = require('mysql');
const fs = require('fs');
const connection = cUtil.connection;

function postPushAlarm(req, res) {
    var token = req.headers['x-access-token'];
    var userSeq = req.body.userSeq;
    var message = req.body.message;


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
            } else if(result[0].userType == "tutor"){
                cUtil.sendPushMessage(userSeq,"이미지수학 알림" ,message);
                res.status(200).send('Success!');
            } else{
                console.log("User Type Error")
                res.status(500).send("DB ERROR!");
            }
        });
    }
}

router.post('/postPush', postPushAlarm);

module.exports = router;