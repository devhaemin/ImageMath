let express = require('express');
let router = express.Router();

const cUtil = require('../../customUtil');


const BOARD_ASSIGNMENT_SUBMIT_FILE = "assignment-submit-image";
const BOARD_ASSIGNMENT_ANSWER_FILE = "assignment-answer"
const BOARD_TEST_ANSWER_FILE = "test-submit-answer"
const BOARD_QUESTION_ATTACH_FILE = "question-submit-files"
const BOARD_ANSWER_ATTACH_FILE = "answer-submit-files";
const BOARD_NOTICE_ATTACH_FILE = 'notice-submit-images';


const connection = cUtil.connection;


router.get("/assignment/ownsubmit", getAssignmentSubmitFiles)
router.get("/assignment/answer", getAssignmentAnswer)
router.get("/test/answer",getTestAnswer)
router.get("/question/attachedFile", getQuestionAttachFile)
router.get("/answer/attachedFile", getAnswerAttachFile)
router.get("/notice/attachedFile", getNoticeAttachFile)

/**
 * @api file/assignment/ownsubmit 과제 제출된 파일 확인
 * @apiName getOwnAssignmentSubmitFiles
 * @apiGroup ServerFile
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission student
 * @apiParam Int assignmentSeq 과제 번호
 *
 *
 */
function getAssignmentSubmitFiles(req, res) {

    const userInfo = req.query.userInfo;
    const assignmentSeq = req.query.assignmentSeq;

    if(!userInfo){
        res.status(400).send("잘못된 요청입니다.");
    }else {
        connection.query("SELECT * FROM FileInfo WHERE boardType = ? and postSeq = ? and userSeq = ? order by uploadTime desc",
            [BOARD_ASSIGNMENT_SUBMIT_FILE, assignmentSeq, userInfo.userSeq], function (err, result) {
                if (err) {
                    console.log(err);
                    res.status(400).send(err);
                } else {
                    res.status(200).send(result);
                }
            });
    }
}

/**
 * @api file/assignment/answer 과제 답안지 파일 확인
 * @apiName getAssignmentAnswer
 * @apiGroup ServerFile
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 * @apiParam Int assignmentSeq 과제 번호
 *
 *
 */

function getAssignmentAnswer(req, res){
    const userInfo = req.query.userInfo;
    const assignmentSeq = req.query.assignmentSeq;

    if(!userInfo){
        res.status(400).send("잘못된 요청입니다.");
    }else {
        connection.query("SELECT * FROM FileInfo WHERE boardType = ? and userSeq = ? and postSeq = ?",
            [BOARD_ASSIGNMENT_ANSWER_FILE, userInfo.userSeq, assignmentSeq], function (err, result) {
                if(err){
                    res.status(400).send("Server Error!");
                }else{
                    res.status(200).send(result);
                }
            })
    }
}

/**
 * @api file/test/answer 테스트 답안지 파일 확인
 * @apiName getTestAnswer
 * @apiGroup ServerFile
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 * @apiParam Int testSeq 테스 번호
 *
 *
 */

function getTestAnswer(req, res){
    const userInfo = req.query.userInfo;
    const testSeq = req.query.testSeq;

    if(!userInfo){
        res.status(400).send("잘못된 요청입니다.");
    }else {
        connection.query("SELECT * FROM FileInfo WHERE boardType = ? and userSeq = ? and postSeq = ?",
            [BOARD_TEST_ANSWER_FILE, userInfo.userSeq, testSeq], function (err, result) {
                if(err){
                    res.status(400).send("Server Error!");
                }else{
                    res.status(200).send(result);
                }
            })
    }
}

/**
 * @api {get} /file/question/attachedFile 질문 첨부파일
 * @apiName GET question attached file
 * @apiGroup ServerFile
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 */

function getQuestionAttachFile(req, res) {
    const userInfo = req.userInfo;
    const questionSeq = req.query.questionSeq
    if (!userInfo) {
        res.status(400).send("토큰이 만료되었습니다.");
    } else {
        connection.query("select * from FileInfo where boardType = ? and postSeq = ?", [BOARD_QUESTION_ATTACH_FILE, questionSeq], function (err, questionFiles) {
            if (err) {
                console.log(err);
                res.status(400).send("질문파일 SQL 에러");
            } else {
                res.status(200).send(questionFiles);
            }
        })
    }
}

/**
 * @api {get} /file/answer/attachedFile 답변 목록
 * @apiName Get Answer List by questionSeq
 * @apiGroup ServerFile
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 *
 */

function getAnswerAttachFile(req, res) {
    const userInfo = req.userInfo;
    const answerSeq = req.query.answerSeq;
    if (userInfo) {
        res.status(400).send("토큰이 만료되었습니다.");
    } else {
        connection.query("select * from FileInfo where boardType = ? and postSeq = ?", [BOARD_ANSWER_ATTACH_FILE, answerSeq],
            function (err, fileList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("SQL error!");
                } else {
                    res.status(200).send(fileList);
                }
            });
    }
}


/**
 * @api {get} file/notice/attachedFile 공지사항 첨부파일
 * @apiName Get Notice attached file list
 * @apiGroup Notice
 * @apiHeader x-access-token 사용자 액세스 토큰
 * @apiPermission normal
 * @apiParam {Int} noticeSeq
 *
 */

function getNoticeAttachFile(req, res) {
    const userInfo = req.userInfo;
    const noticeSeq = req.query.noticeSeq;
    if (userInfo) {
        connection.query("select * from FileInfo where boardType = ? and postSeq = ?", [BOARD_NOTICE_ATTACH_FILE, noticeSeq],
            function (err, fileList) {
                if (err) {
                    console.log(err);
                    res.status(400).send("File Query Error!");
                } else {
                    res.status(200).send(fileList);
                }
            });
    } else {
        console.log("token error");
        res.status(403).send("token error!");
    }

}
module.exports = router;
