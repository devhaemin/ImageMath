const jwt = require('jsonwebtoken');
const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');
const connection = cUtil.connection;

const secretKey1 = "stu";
const secretKey2 = "tut";

verfiyToken = function (token) {
    return new Promise(function (resolve) {
        jwt.verify(token, secretKey1, function (err, decodedToken) {
            if (err) {
                jwt.verify(token, secretKey2, function (err, decodedToken) {
                    if (err) {
                        return res.status(400).send('UNCORRECT SECRETKEY');
                    }
                    resolve([false, decodedToken, secretKey2]);
                })
            } else {
                resolve([true, decodedToken, secretKey1]);
            }
        })
    })
}


router.get('/', function (req, res, next) {

    const token = req.headers['x-access-token'];

    if (!token) {
        res.status(400).send('TOKEN IS REQUIRED');
    }
    ;

    verifyToken(token)
        .then(function (resolve) {
            const whichOne = resolve[0];
            req.decoded = resolve[1];
            req.secret = resolve[2];

            let sql = "SELECT userSeq FROM UserInfo WHERE email = ? AND userSeq = ?";
            if (whichOne == false) {
                sql = "SELECT userSeq FROM TutorPrivacy WHERE email = ? AND userSeq = ?";
            }
            const param = [req.decoded.email, req.decoded.userSeq];

            connection.query(sql, param, function (err, rows, field) {
                if (err) {
                    console.error(err.stack);
                    res.status(500).send('500 SERVER ERROR, db7');

                } else if (rows.length != 1) {
                    res.status(400).send('UNCORRECT USERINFO');
                } else {
                    next();
                }
            })

        })
});

module.exports = router;