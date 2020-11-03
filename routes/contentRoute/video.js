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
router.post('/', postVideoFile);
router.delete('/:videoSeq', deleteVideoPost);
router.update('/:videoSeq', modifyVideoFile);


/**
 * @api {get} video 비디오 포스팅 리스트
 * @apiName getVideoPostList
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 *
 */
function getVideoPostList(req, res){

}

/**
 * @api {MultiPart} {post} video 비디오 포스팅 작성
 * @apiName postVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam String title 제목
 * @apiParam String contents 내용
 * @apiParam Video video 영상 파일
 *
 *
 */
function postVideoFile(req, res){

}

/**
 * @api {delete} video/:videoSeq 해당 영상 포스팅 삭제
 * @apiName deleteVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam Int videoSeq 영상 번호
 *
 *
 */
function deleteVideoPost(req, res){

}

/**
 * @api {update} video/:videoSeq 영상 포스팅 수정
 * @apiName modifyVideoFile
 * @apiGroup Video
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission tutor
 * @apiParam Int videoSeq 영상 번호
 *
 *
 */
function modifyVideoFile(req, res){

}






module.exports = router;
