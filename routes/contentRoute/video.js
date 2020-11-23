let express = require('express');
let router = express.Router();

const cUtil = require('../../customUtil');
const multer = require('multer');
const multerS3 = require('multer-s3');
const BOARD_VIDEO_ATTACH_VIDEO = 'video-attached';

let AWS = require('aws-sdk');
AWS.config.loadFromPath("./config.json");
let s3 = new AWS.S3();

const connection = cUtil.connection;

const upload = multer({
    storage: multerS3({
        s3: s3,
        bucket: 'imagemath',
        ContentType: multerS3.AUTO_CONTENT_TYPE,
        key: (req, file, cb) => {
            console.log(file);
            let str = file.originalname;
            let res = str.substring(str.length - 5, str.length);
            cb(null, Date.now() + "_" + res);
        },
        acl: 'public-read',
    }),
    limits: {fileSize: 5 * 1024 * 1024 * 1024},
});

router.get('/', getVideoPostList);
router.get('/lecture/:lectureSeq', getVideoByLecture);
router.patch('/:videoSeq/:userSeq', modifyVideoPermission);
router.post('/', upload.single('video'), postVideoFile);
router.delete('/:videoSeq', deleteVideoPost);
router.post('/:videoSeq', modifyVideoFile);

/**
 * @api {get} video/lecture/:lectureSeq 비디오 수업별 포스팅 리스트
 * @apiName getVideoByLecture
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 * @apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     [
 *       {
 *       "videoSeq": 1,
 *       "title": "동영상 제목",
 *       "contents": "동영상 내용",
 *       "uploadTime": 1604383318520,
 *       "userSeq": 13
 *       },
 *       {
 *       "videoSeq": 2,
 *       "title": "동영상 두번째 제목",
 *       "contents": "동영상 내용",
 *       "uploadTime": 1604383328520,
 *       "userSeq": 15
 *       }
 *     ]
 *
 */

function getVideoByLecture(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.lectureSeq;
    if (!userInfo) {
        res.status(403).send("Token Expired!");
    }else if(userInfo.userType !== 'tutor') {
        connection.query("SELECT va.*, vi.* FROM VideoAdm AS va JOIN Video AS vi WHERE va.userSeq = ? and va.videoSeq = vi.videoSeq and va.hasAccess = 1 and vi.lectureSeq = ? ORDER BY vi.uploadTime desc",
            [userInfo.userSeq, lectureSeq],function (err, videoList) {
                if (err) {
                    console.log(err);
                    res.status(500).send("MySQL Query Error!");
                } else {
                    res.status(200).send(videoList);
                }
            })
    }else{
        connection.query("SELECT va.*, vi.* FROM VideoAdm AS va JOIN Video AS vi WHERE va.videoSeq = vi.videoSeq and vi.lectureSeq = ? ORDER BY vi.uploadTime desc",
            [lectureSeq],function (err, videoList) {
                if (err) {
                    console.log(err);
                    res.status(500).send("MySQL Query Error!");
                } else {
                    res.status(200).send(videoList);
                }
            })
    }
}


/**
 * @api {patch} video/:videoSeq/user 비디오 접근 권한 수정
 * @apiName modifyVideoPermission
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 *
 * @apiParam {Number} videoSeq
 * @apiParam {Number} userSeq
 * @apiParam {Number} hasAccess // 0 : false, 1 : true
 *
 * @apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     {}
 *
 */
function modifyVideoPermission(req, res) {
    const userInfo = req.userInfo;
    const videoSeq = req.params.videoSeq;
    const userSeq = req.params.userSeq;
    const hasAccess = req.body.hasAccess;

    if (!userInfo) {
        res.status(403).send("Token Expired!");
    }else if(userInfo.userType !== 'tutor') {
            connection.query("INSERT INTO VideoAdm (videoSeq, userSeq,hasAccess) VALUES (?,?,?) ON DUPLICATE KEY UPDATE hasAccess = ?",[videoSeq, userSeq, hasAccess, hasAccess], function (err, result) {
                if(err){
                    console.log(err);
                    res.status(500).send({"message" : "Internal MySQL Error!"});
                }else{
                    res.status(200);
                }
            });
    }else{
        res.status(403).send("Token error!");
    }
}

/**
 * @api {get} video 비디오 포스팅 리스트
 * @apiName getVideoPostList
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 * @apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     [
 *       {
 *       "videoSeq": 1,
 *       "title": "동영상 제목",
 *       "contents": "동영상 내용",
 *       "uploadTime": 1604383318520,
 *       "userSeq": 13
 *       },
 *       {
 *       "videoSeq": 2,
 *       "title": "동영상 두번째 제목",
 *       "contents": "동영상 내용",
 *       "uploadTime": 1604383328520,
 *       "userSeq": 15
 *       }
 *     ]
 *
 */
function getVideoPostList(req, res) {
    const userInfo = req.userInfo;
    if (!userInfo) {
        res.status(403).send("Token Expired!");
    }else if(userInfo.userType !== 'tutor') {
        connection.query("SELECT va.*, vi.* FROM VideoAdm AS va JOIN Video AS vi WHERE va.userSeq = ? and va.videoSeq = vi.videoSeq and va.hasAccess = 1 ORDER BY vi.uploadTime desc",
            userInfo.userSeq,function (err, videoList) {
                if (err) {
                    console.log(err);
                    res.status(500).send("MySQL Query Error!");
                } else {
                    res.status(200).send(videoList);
                }
            })
    }else{
        connection.query("SELECT va.*, vi.* FROM VideoAdm AS va JOIN Video AS vi WHERE va.videoSeq = vi.videoSeq ORDER BY vi.uploadTime desc",
            userInfo.userSeq,function (err, videoList) {
                if (err) {
                    console.log(err);
                    res.status(500).send("MySQL Query Error!");
                } else {
                    res.status(200).send(videoList);
                }
            })
    }
}

/**
 * @api {MultiPart} video 비디오 포스팅 작성
 * @apiName postVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam String title 제목
 * @apiParam String contents 내용
 * @apiParam Video video 영상 파일
 *
 * @apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     {}
 */
function postVideoFile(req, res) {
    const userInfo = req.userInfo;

    console.log(req.file);
    if (req.file) {
        if (!userInfo) {
            res.status(403).send("Token Expired!");
        } else if (userInfo.userType !== "tutor") {
            res.status(403).send("Only tutor can post");
        } else {
            const params = {
                "title": req.body.title,
                "contents": req.body.contents,
                "userSeq": userInfo.userSeq,
                "uploadTime": Date.now()
            }
            connection.query('INSERT INTO Video SET ?', params,
                function (err, result) {
                    if (err) {
                        console.log(err);
                        res.status(500).send("MySQL Query Error!");
                    } else {
                        console.log(result);
                        insertVideoFileInfo(userInfo, result.insertId, req.file);
                        res.status(200).send({});
                    }
                });
        }
    } else {
        res.status(400).send({"message": "Please upload with file"});
    }

}

function insertVideoFileInfo(userInfo, videoSeq, file) {

    const fileParams = {
        boardType: BOARD_VIDEO_ATTACH_VIDEO,
        postSeq: videoSeq,
        bucket: 'imagemath',
        fileUrl: file.location,
        fileName: file.key,
        fileType: 'video',
        uploadTime: Math.floor(new Date().getTime() / 1000),
        userSeq: userInfo.userSeq
    };
    connection.query('INSERT INTO FileInfo SET ?', fileParams,
        function (err, result) {
            if (err) {
                console.log(err);
            }
        })

}

/**
 * @api {delete} video/:videoSeq 해당 영상 포스팅 삭제
 * @apiName deleteVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam Int videoSeq 영상 번호
 *
 * @apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     {}
 *
 */
function deleteVideoPost(req, res) {
    const userInfo = req.userInfo;
    const videoSeq = req.params.videoSeq;
    if (!userInfo) {
        res.status(403).send("Token Expired!");
    } else {
        connection.query('delete from FileInfo where postSeq = ?', videoSeq,
            function (err, result) {
                if (err) {
                    console.log(err);
                } else {

                }
            }
        );
        connection.query('delete from Video where videoSeq = ?', videoSeq,
            function (err, result) {
                if (err) {
                    console.log(err);
                } else {
                    res.status(200).send({});
                }
            });
    }
}

/**
 * @api {post} video/:videoSeq 영상 포스팅 수정
 * @apiName modifyVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam Int videoSeq 영상 번호
 *
 *@apiSuccessExample {json} Success-Response:
 *     HTTP/1.1 200 OK
 *     {}
 */
function modifyVideoFile(req, res) {

}


module.exports = router;
