const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');

const connection = cUtil.connection;

router.get('/academy', academy);
router.get('/:userSeq', getUserInfo);


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
        } else if (requestUser.userType === "student") {
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



module.exports = router;
