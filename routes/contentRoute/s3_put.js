let express = require('express');
let router = express.Router();
let AWS = require('aws-sdk');
let fs = require('fs');
AWS.config.region = 'ap-northeast-2';
let s3 = new AWS.S3();

router.post('/upload', function (req, res) {
    let noticeSeq = req.body.noticeSeq;
    let bucket = 'imagemath-notice/' + noticeSeq;
    let param = {
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