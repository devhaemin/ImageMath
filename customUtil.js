const mysql = require('mysql');
var util = {};
var admin = require('firebase-admin');
//var FCM = require('fcm-node');
//var serverKey = require('./imagemath-server-firebase-adminsdk-tkois-b88a4fdbae.json');
//var fcm = new FCM(serverKey);
var AWS = require('aws-sdk');
var fs = require('fs');
AWS.config.region = 'ap-northeast-2';
const multer = require('multer');
const multerS3 = require('multer-s3');
var s3 = new AWS.S3();
var serviceAccount = require("./key/imagemath-server-firebase-adminsdk-tkois-54443dad9e.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://imagemath-server.firebaseio.com"
});



util.connectionQuery = function(connection, sql, params) {
    return new Promise((resolve, reject) => {
        if (params.length == 0) {
            connection.query(sql, function(error, results, next) {
                if (err) {
                    reject(err);
                } else {
                    resolve(results);
                }
            });
        } else {
            connection.query(sql, params, function(error, results, next) {
                if (err) {
                    reject(err);
                } else {
                    resolve(results);
                }
            })
        }
    });
}


util.connection = mysql.createConnection({
    host: 'localhost',
    user: 'imagemath',
    database: 'ImageMath',
    password: 'asdf1234',
    port: '3306',
    multipleStatements: true
});
util.connect = function handleDisconnect() {
  util.connection.connect(function(err) {
    if(err) {
      console.log('error when connecting to db:', err);
      setTimeout(handleDisconnect, 2000);
    }
  });

  util.connection.on('error', function(err) {
    console.log('db error', err);
    if(err.code === 'PROTOCOL_CONNECTION_LOST') {
      return handleDisconnect();
    } else {
      throw err;
    }
  });
};

util.tokenMiddleWare = function(req, res, next){
    var token = req.headers['x-access-token'];
    if(token){
        util.connection.query("select * from UserInfo where accessToken = ?", token, function (err ,userInfo) {
            if(err){
                console.log(err);
                next();
            }else if(userInfo){
                next();
            }else if(userInfo.length === 0){
                next();
            }else{
                req.userInfo = userInfo[0];
                next();
            }
        });
    }else{
        next();
    }
};


util.isDelivered = function(arr){
    isDelivered = true;
    for (var i = 0; i < arr.length; i++) {
        if (!toString(arr[i])) {
            isDelivered = false;
        }
    }
    return isDelivered;
};

util.checkAuth = function(req, res){
    var token = req.headers['x-access-token'];
    this.connection.query("select * from UserInfo where token = ?", token, function(err, userInfos){
        if(err){
            res.send(500)
        }
    })
};

util.sendPushMessage = sendPushMessage;
function sendPushMessage(targetSeq, title, content){
    var connection = util.connection;
    if(targetSeq == null) return;
    connection.query("select fcmToken from UserInfo where userSeq = ?", targetSeq, function(err, userInfos){
        if(err){
            console.log(err);
        }else{
            var values = {
                userSeq : targetSeq,
                title : title,
                content : content,
                postTime : Date.now()
            };
            connection.query("INSERT INTO Alarm SET ?", values, function(err, result){
                if(err){
                    console.log(err);
                }else if(userInfos != undefined && userInfos.length != 0){
                    var fcm_target_token = userInfos[0].fcmToken;
                    if(fcm_target_token != null){
                    var fcm_message = {
                        notification: {
                            title : title,
                            body : content
                        },
                        token : fcm_target_token
                    };
                    admin.messaging().send(fcm_message)
                    .then(function(res){
                        
                    })
                    .catch(function(err){
                        console.log(err);
                    })
                }
                }else{
                    console.log("No User!");
                }
            })
        }
    })   
}

module.exports = util;
