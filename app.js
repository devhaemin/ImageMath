var createError = require('http-errors');
var express = require('express');
var path = require('path');
const bodyParser = require('body-parser');
const cUtil = require('./customUtil');
var cookieParser = require('cookie-parser');
var cors = require('cors');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static('apidoc'));
app.use(express.static(path.join(__dirname, 'public')));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
  res.header('Access-Control-Allow-Headers', 'content-type, x-access-token'); //1

  next();
});
app.use(cUtil.tokenMiddleWare);

app.use('/', indexRouter);
app.use('/users', usersRouter);

const authRoute = require('./routes/basicRoute/auth');
app.use('/auth', authRoute);
// ALL : Login, (token : refresh, isAuthenticated), account
// -------------------------------------------------------//

const alarmRoute = require('./routes/basicRoute/alarm');
app.use('/alarm', alarmRoute);

const settingRoute = require('./routes/basicRoute/setting');
app.use('/setting', settingRoute);

const fileRoute = require('./routes/contentRoute/serverfile');
app.use('/file',fileRoute);

const accountRoute = require('./routes/basicRoute/account');
app.use('/account', accountRoute);
// Tutor : ChangePw, AccountAdm, *StudentInfo*
// Student : ChangePw, AccountAdm

const lectureRoute = require('./routes/contentRoute/lecture');
app.use('/lecture', lectureRoute);
// Tutor : LectureInfo, StudentAdm, Recognition, AddLecture
// Student : LectureInfo

const noticeRoute = require('./routes/contentRoute/notice');
app.use('/notice', noticeRoute);
// Tutor : Notice, NoticeEdit
// Student :  Notice
// Tutor : Notice, NoticeEdit
// Student :  Notice

const assignmentRoute = require('./routes/contentRoute/assignment');
app.use('/assignment', assignmentRoute);
// Tutor : Assginment, AssginmentInfo, AssignmentEdit
// Student : Assginment, AssginmentInfo

const testRoute = require('./routes/contentRoute/test');
app.use('/test', testRoute);
// Tutor : Test, TestInfo, TestEdit
// Student : Test, TestInfo

const qnaRoute = require('./routes/contentRoute/qna');
app.use('/qna', qnaRoute);
// ALL : Qna, Chat

const fcmRoute = require('./routes/basicRoute/fcmPush');
app.use('/push', fcmRoute);

const videoRoute = require('./routes/contentRoute/video');
app.use('/video', videoRoute);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

setInterval(function () {
  cUtil.connection.query('SELECT 1');
}, 5000);

module.exports = app;
