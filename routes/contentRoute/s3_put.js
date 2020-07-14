var express = require('express');
var router = express.Router();
var AWS = require('aws-sdk');
var fs = require('fs');
AWS.config.region = 'ap-northeast-2';
var s3 = new AWS.S3();

router.post('/upload', function (req, res) {
    var noticeSeq = req.body.noticeSeq;
    var bucket = 'imagemath-notice/' + noticeSeq;
    var param = {
        'Bucket': bucket,
        'Key': 'aaa.png',
        'ACL': 'public-read',
        'Body': req.body.file,
        'ContentType': 'image/png'
    };
    s3.putObject(param, function (err, data) {
        if (err) {
            console.log(err);
            res.statu(400).send("이미지 업로드 에러");
        } else {
            console.log(data);
            res.status(200).send("image upload");
        }
    })
});

module.exports = router;