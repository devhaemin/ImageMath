const express = require('express');
const router = express.Router();
const cUtil = require('../../customUtil');
const connection = cUtil.connection;


router.get('/', lecture);
router.get('/student', studentLectureList);
router.get('/student/:lectureSeq', getStudentList);
router.patch('/recognition/:lectureSeq', recognitionLec);
router.delete('/student/:lectureSeq/:userSeq', deleteStudent);
router.post('/add', addLecture);
router.get('/recognition/:lectureSeq', getRecongition);
router.post('/add/student', addrecognition);
router.get('/tutor', getStudentLectureList);
router.patch('/:lectureSeq', setExpiredLecture);
router.delete('/:lectureSeq', deleteLecture);

/**
 *
 * @api {get} lecture 전체 수업 목록
 * @apiName Get LectureList
 * @apiGroup Lecture
 * @apiPermission normal
 *
 * @apiParam {Boolean} exceptExpired
 */

function lecture(req, res) {
    const exceptExpired = req.query.exceptExpired;
    if(exceptExpired && exceptExpired === 'true'){
        const sql = "select lectureSeq, name, academySeq, time, weekDay, totalDate, week, studentNum, reqStudentCnt, academyName, IF(isExpired,'true','false') as isExpired from LectureInfo where isExpired = ? order by lectureSeq DESC";
        connection.query(sql,0 ,function (err, result) {
            if (err) {
                console.log(err);
                res.status(400).send("lecture search error");
            } else {
                console.log("lecture List");
                res.status(200).send(result);
            }
        })
    }else{
        const sql = "select lectureSeq, name, academySeq, time, weekDay, totalDate, week, studentNum, reqStudentCnt, academyName, IF(isExpired,'true','false') as isExpired from LectureInfo order by lectureSeq DESC";
        connection.query(sql, function (err, result) {
            if (err) {
                console.log(err);
                res.status(400).send("lecture search error");
            } else {
                console.log("lecture List");
                res.status(200).send(result);
            }
        })
    }

}

/**
 *
 * @api {get} lecture/student 학생 전용 수업 목록
 * @apiName Get Student LectureList
 * @apiGroup Lecture
 * @apiPermission student
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {Number} id Users unique ID.
 *
 */

function studentLectureList(req, res) {

    const userInfo = req.userInfo;
    const exceptExpired = req.query.exceptExpired;

    if (userInfo && userInfo.userType === "student") {
        if(exceptExpired && exceptExpired === 'true') {
            connection.query('select li.lectureSeq, name, academySeq, li.time, weekDay, totalDate, week, studentNum, reqStudentCnt, academyName, IF(isExpired,\'true\',\'false\') as isExpired from LectureAdm AS la JOIN LectureInfo AS li ON la.userSeq = ? and la.lectureSeq = li.lectureSeq and li.isExpired = ?'
                ,[userInfo.userSeq, 0], function (err, result) {
                    if(err){
                        console.log(err);
                        res.status(500).send({"message" : "Internal SQL Error!"});
                    }else{
                        res.status(200).send(result);
                    }
                })
        }else{
            connection.query('select li.lectureSeq, name, academySeq, li.time, weekDay, totalDate, week, studentNum, reqStudentCnt, academyName, IF(isExpired,\'true\',\'false\') as isExpired from LectureAdm AS la JOIN LectureInfo AS li ON la.userSeq = ? and la.lectureSeq = li.lectureSeq'
                ,[userInfo.userSeq], function (err, result) {
                    if(err){
                        console.log(err);
                        res.status(500).send({"message" : "Internal SQL Error!"});
                    }else{
                        res.status(200).send(result);
                    }
                })
        }
    } else if (!userInfo) {
        res.status(400).send("권한이 없습니다.");
    } else {
        console.log("permission error : " + userInfo.userType);
        res.status(400).send("권한이 없습니다.");
    }

}

/**
 *
 * @api {get} lecture/student/:lectureSeq 수업별 학생 목록
 * @apiName Get Student LectureList
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {Number} id Users unique ID.
 *
 */

function getStudentList(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;

    if (!userInfo || userInfo.userType === "student") {
        console.log("잘못된 토큰입니다.");
        res.status(400).send("잘못된 토큰입니다.");
    } else {
        connection.query("select * from LectureAdm as la JOIN UserInfo as ui where la.lectureSeq = ? and la.userSeq = ui.userSeq", [lectureSeq], function (err, studentInfos) {
            if (err) {
                console.log(err);
                res.status(400);
            } else {
                res.status(200).send(studentInfos);
            }
        })
    }
}

/**
 *
 * @api {patch} lecture/student/:lectureSeq 수업 인증 확인
 * @apiName Patch Request Lecture recognition
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {Int} studentSeq
 * @apiParam {String} isAccept
 *
 *
 */
//todo: studentSeq 변수명 확인하기

function recognitionLec(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;
    const studentSeq = req.body.studentSeq;
    const isAccept = req.body.isAccept;

    if (!userInfo) {
        res.status(400).send("토큰이 만료되었습니다.");
    } else if (userInfo.userType === "tutor") {
        connection.query("select ui.userSeq, ui.studentCode, li.name from UserInfo AS ui JOIN LectureInfo AS li where ui.userSeq = ? and li.lectureSeq = ?", [studentSeq, lectureSeq], function (err, result) {
            if (err) {
                console.log(err);
                res.status(400).send("학생을 찾을 수 없습니다.");
            } else if (result.length === 0) {
                res.status(400).send("학생을 찾을 수 없습니다.");
            } else {
                if (isAccept === 'true') {
                    let timestamp = new Date().getTime();
                    let params = {
                        userSeq: result[0].userSeq,
                        lectureSeq: lectureSeq,
                        time: timestamp,
                        studentCode: result[0].studentCode
                    }
                    connection.query("insert into LectureAdm set ? ", params, function (error, results, nexts) {
                        if (error) {
                            console.log(error);
                        } else {
                            console.log("insert success");

                            cUtil.sendPushMessage(studentSeq, result[0].name + "", "수업 추가 요청이 승인되었습니다.");
                            res.status(200).send("인증이 완료되었습니다.");
                        }
                    });
                    connection.query("update LectureInfo set studentNum = studentNum+1 where lectureSeq = ?", lectureSeq, function (err) {
                        if (err) {
                            console.log(err);
                        }
                    });

                    connection.query("delete from Recognition where userSeq = ? and lectureSeq = ?", [result[0].userSeq, lectureSeq], function (err) {
                        if (err) {
                            console.log("delete error");
                            console.log(error);
                        } else {
                            console.log("delete Recognition");
                        }
                    })

                    connection.query("INSERT IGNORE INTO AssignmentAdm (userSeq,studentCode, assignmentSeq, uploadTime) SELECT userSeq,studentCode, assignmentSeq,uploadTime FROM LectureAdm as la JOIN AssignmentInfo AS ai JOIN (SELECT ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000) AS uploadTime) AS time where la.lectureSeq = ai.lectureSeq;"
                        , function (err) {
                            if (err) {
                                console.log("add assignment error!");
                            }
                        });
                } else {
                    connection.query("delete from Recognition where userSeq = ? and lectureSeq = ?", [result[0].userSeq, lectureSeq], function (err) {
                        if (err) {
                            console.log("delete error");
                            console.log(error);
                        } else {
                            console.log("delete Recognition");
                        }
                    })

                    res.status(200).send("인증이 취소 되었습니다.");
                }
            }
        })
    } else {
        console.log("permission error");
        res.status(400).send("권한이 없습니다.");
    }
}

/**
 *
 * @api {delete} lecture/student/:lectureSeq/:userSeq 수업에서 학생 삭제
 * @apiName Delete student on lecture for tutor
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 *
 *
 */

function deleteStudent(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;
    const userSeq = req.params.userSeq;

    if (!userInfo || userInfo.userType === "student") {
        console.log("잘못된 토큰입니다.");
        res.status(400).send("잘못된 토큰입니다.");
    } else {
        connection.query("delete from LectureAdm where lectureSeq = ? and userSeq = ?", [lectureSeq, userSeq], function (err) {
            if (err) {
                console.log(err);
                res.status(400);
            } else {
                res.status(200);
            }
        })
    }
}
/**
 *
 * @api {get} lecture/tutor 튜터용 학생의 수업 목록 조회
 * @apiName Get student lecture list for tutor
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {String} userSeq 학생 시퀀스 번호
 *
 *
 */
function getStudentLectureList(req, res) {

    const userInfo = req.userInfo;
    const userSeq = req.query.userSeq;
    if (!userInfo || userInfo.userType === "student") {
        console.log("잘못된 토큰 입니다.");
        res.status(400).send("잘못된 토큰 입니다.");
    } else {
        connection.query("select la.*, li.academyName, li.academySeq, li.name, li.reqStudentCnt, li.studentNum, li.time, li.totalDate, li.week, li.weekDay, IF(li.isExpired, 'true', 'false') as isExpired from LectureAdm AS la JOIN LectureInfo AS li ON la.lectureSeq = li.lectureSeq and la.userSeq = ?", userSeq, function (err, lectureList) {
            if (err) {
                console.log(err);
                res.status(400).send("SQL Error");
            } else {
                res.status(200).send(lectureList);
            }
        });
    }

}

/**
 *
 * @api {post} lecture/add 신규 수업 개설
 * @apiName Add Lecture
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiUse AcademyInfo
 * @apiUse LectureInfo
 *
 *
 */

function addLecture(req, res) {
    const userInfo = req.userInfo;

    const academySeq = req.body.academySeq;
    const academyName = req.body.academyName;
    const name = req.body.name;
    const weekDay = req.body.weekDay;
    const time = req.body.time;
    const totalDate = req.body.totalDate;
    const week = req.body.week;
    const studentNum = req.body.studentNum;

    if (!userInfo) {
        res.status(400).send("토큰이 만료되었습니다.");
    } else if (userInfo.userType === "tutor") {
        const sql = "INSERT INTO LectureInfo SET ?";
        const param = {
            academySeq: academySeq,
            academyName: academyName,
            name: name,
            weekDay: weekDay,
            time: time,
            week: week,
            totalDate: totalDate,
            studentNum: studentNum
        };

        connection.query(sql, param, function (err) {
            if (err) {
                console.error(err);
                res.status(500).send('500 SERVER ERROR');
            } else {
                console.log("lectureadd");
                res.status(200).send("수업이 개설되었습니다.");
            }
        })
    } else {
        console.log("permission error");
        res.status(400).send("권한이 없습니다");
    }

}

/**
 *
 * @api {get} lecture/recognition/:lectureSeq 수업 인증 요청 목록
 * @apiName Get Lecture recognition request list
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 *
 *
 */


function getRecongition(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;

    if (!userInfo) {
        res.status(400).send("만료된 토큰 입니다.");
    } else if (userInfo.userType === "tutor") {
        let sql2 = "select * from Recognition where lectureSeq = ? ";
        connection.query(sql2, lectureSeq, function (error, results) {
            if (error) {
                console.log(error);
                res.status(400).send("수업 시퀀스 에러");
            } else {
                res.status(200).send(results);
            }
        })
    } else {
        console.log("permission error : " + userInfo.userType);
        res.status(400).send("권한이 없습니다.");
    }
}

/**
 *
 * @api {post} lecture/add/student 인증 요청하기
 * @apiName Request Lecture Recognition
 * @apiGroup Lecture
 * @apiPermission student
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {String} lectureSeq
 *
 *
 */


function addrecognition(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.body.lectureSeq;

    if (!userInfo) {
        res.status(400).send("토큰이 만료되었습니다.");
    } else {
        let userSeq = userInfo.userSeq;
        connection.query("SELECT lectureSeq FROM Recognition WHERE userSeq = ? AND lectureSeq = ? UNION SELECT lectureSeq FROM LectureAdm WHERE userSeq = ? AND lectureSeq = ?", [userInfo.userSeq, lectureSeq, userInfo.userSeq, lectureSeq], function (err, result) {
            if (err) {
                console.log(err);
            } else if (result.length === 0) {
                let timestamp = new Date().getTime();
                let params = {
                    userSeq: userSeq,
                    lectureSeq: lectureSeq,
                    time: timestamp,
                }
                connection.query("insert into Recognition set ? ", params, function (error, results, nexts) {
                    if (error) {
                        console.log(error);
                    } else {
                        console.log("insert success");
                        res.status(200).send("신청 완료");
                    }
                })
            } else {
                console.log("이미 신청되있음");
                res.status(400).send("이미 신청이 되어있습니다.");
            }
        })
    }
}

/**
 *
 * @api {patch} lecture/:lectureSeq 수업 폐강 처리
 * @apiName Patch Lecture Expired
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {String} isExpired
 *
 *
 */

function setExpiredLecture(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;
    const isExpired = req.body.isExpired;
    if (!userInfo || userInfo.userType !== 'tutor') {
        res.status(403).send({"message": "Token ERROR!"});
    } else {
        connection.query('UPDATE LectureInfo SET isExpired = ? WHERE lectureSeq = ?', [isExpired, lectureSeq], function (err) {
            if (err) {
                console.log(err);
                res.status(500).send({"message": "Internal Server MYSQL ERROR!"});
            } else {
                res.status(200).send({});
            }
        })
    }
}

/**
 *
 * @api {delete} lecture/:lectureSeq 수업 삭제 처리
 * @apiName Delete Lecture
 * @apiGroup Lecture
 * @apiPermission tutor
 *
 * @apiHeader {String} x-access-token 유저 액세스 토큰
 * @apiParam {String} isExpired
 *
 *
 */


function deleteLecture(req, res) {
    const userInfo = req.userInfo;
    const lectureSeq = req.params.lectureSeq;
    if (!userInfo || userInfo.userType !== 'tutor') {
        res.status(403).send({"message": "Token ERROR!"});
    } else {
        connection.query('delete from LectureInfo where lectureSeq = ?', lectureSeq, function (err) {
            if (err) {
                res.status(400).send({});
            } else {
                res.status(200).send({});
            }
        })
    }
}


module.exports = router;
