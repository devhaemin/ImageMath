const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const cUtil = require('./customUtil');
cUtil.connect();
app.listen(3000, function () {
    console.log('3000 running');
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    res.header('Access-Control-Allow-Headers', 'content-type, x-access-token'); //1

    next();
});
//*�� 8�� login, setting ,account, lecture, notice, assignment, test, qna*

const authRoute = require('./basicRoute/auth');
app.use('/auth', authRoute);
// ALL : Login, (token : refresh, isAuthenticated), account

// ----------------- checkAuthByToken --------------------//
const checkAuth = require('./basicRoute/checkAuth');
app.use('/checkAuth', checkAuth);
// -------------------------------------------------------//

const alarmRoute = require('./basicRoute/alarm');
app.use('/alarm', alarmRoute);

const settingRoute = require('./basicRoute/setting');
app.use('/setting', settingRoute);
// ALL : ȯ�漳��(��Ÿ �˸� ����, ���̼���)

const uploadFile = require('./contentRoute/s3_put.js');
app.use('/File', uploadFile);

const accountRoute = require('./basicRoute/account');
app.use('/account', accountRoute);
// Tutor : ChangePw, AccountAdm, *StudentInfo*
// Student : ChangePw, AccountAdm

const lectureRoute = require('./contentRoute/lecture');
app.use('/lecture', lectureRoute);
// Tutor : LectureInfo, StudentAdm, Recognition, AddLecture
// Student : LectureInfo

const noticeRoute = require('./contentRoute/notice');
app.use('/notice', noticeRoute);
// Tutor : Notice, NoticeEdit
// Student :  Notice
// Tutor : Notice, NoticeEdit
// Student :  Notice

const assignmentRoute = require('./contentRoute/assignment');
app.use('/assignment', assignmentRoute);
// Tutor : Assginment, AssginmentInfo, AssignmentEdit
// Student : Assginment, AssginmentInfo

const testRoute = require('./contentRoute/test');
app.use('/test', testRoute);
// Tutor : Test, TestInfo, TestEdit
// Student : Test, TestInfo

const qnaRoute = require('./contentRoute/qna');
app.use('/qna', qnaRoute);
// ALL : Qna, Chat

const fcmRoute = require('./basicRoute/fcmPush');
app.use('/push', fcmRoute);

app.use((req, res, next) => {
    res.status(404).send(['404 NOT FOUND']);
});

app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send(['SERVER ERROR']);
});

setInterval(function () {
    cUtil.connection.query('SELECT 1');
}, 5000);

/**
 * @api {get} /user/:id Get User information and Date of Registration.
 * @apiVersion 0.2.0
 * @apiName GetUser
 * @apiGroup User
 *
 * @apiParam {Number} id Users unique ID.
 *
 * @apiSuccess {String} firstname  Firstname of the User.
 * @apiSuccess {String} lastname   Lastname of the User.
 * @apiSuccess {Date}   registered Date of Registration.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "firstname": "John",
 *       "lastname": "Doe"
 *     }
 *
 * @apiError UserNotFound The id of the User was not found.
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 404 Not Found
 *     {
 *       "error": "UserNotFound"
 *     }
 */
