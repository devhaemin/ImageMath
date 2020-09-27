define({ "api": [
  {
    "type": "get",
    "url": "assignment",
    "title": "수업별 과제 리스트 출력",
    "name": "Get_Assignment_List",
    "group": "Assignment",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/assignment.js",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "assignment/:assignmentSeq",
    "title": "과제 정보 디테일",
    "name": "Get_StudentAssignment_Detail",
    "group": "Assignment",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/assignment.js",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "assignment/student",
    "title": "학생의 과제 정보 리스트",
    "name": "Get_StudentAssignment_List",
    "group": "Assignment",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/assignment.js",
    "groupTitle": "Assignment"
  },
  {
    "type": "get",
    "url": "assignment/student/:assignmentSeq",
    "title": "학생의 과제 제출 정보",
    "name": "Get_StudentAssignment_Submit_Detail",
    "group": "Assignment",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/assignment.js",
    "groupTitle": "Assignment"
  },
  {
    "type": "post",
    "url": "auth/login",
    "title": "이메일로 토큰 발급하기",
    "name": "ObtainToken_with_email_login",
    "group": "Auth",
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/basicRoute/auth.js",
    "groupTitle": "Auth",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "userSeq",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "birthday",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "accessToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "fcmToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "gender",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "salt",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "userType",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "studentCode",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "schoolName",
            "description": ""
          },
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "registerTime",
            "description": ""
          }
        ]
      }
    }
  },
  {
    "type": "get",
    "url": "auth/autologin",
    "title": "토큰 로그인",
    "name": "ObtainToken_with_email_login",
    "group": "Auth",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/basicRoute/auth.js",
    "groupTitle": "Auth",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "userSeq",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "birthday",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "accessToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "fcmToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "gender",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "salt",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "userType",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "studentCode",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "schoolName",
            "description": ""
          },
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "registerTime",
            "description": ""
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "auth/register",
    "title": "회원가입",
    "name": "Register_with_email",
    "group": "Auth",
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "birthday",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "gender",
            "description": "<p>0: 남자, 1: 여자</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userType",
            "description": "<p>student : 학생 tutor : 조교</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": "<p>휴대폰 번호</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "schoolName",
            "description": "<p>학교 이름 ex)대성고</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "studentCode",
            "description": "<p>학생 스쿨링코드</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/basicRoute/auth.js",
    "groupTitle": "Auth",
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "userSeq",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "birthday",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "accessToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "fcmToken",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "gender",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "salt",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "userType",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "phone",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "studentCode",
            "description": ""
          },
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "schoolName",
            "description": ""
          },
          {
            "group": "200",
            "type": "Int",
            "optional": false,
            "field": "registerTime",
            "description": ""
          }
        ]
      }
    }
  },
  {
    "type": "post",
    "url": "lecture/add",
    "title": "신규 수업 개설",
    "name": "Add_Lecture",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "academySeq",
            "description": "<p>학원 번호</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "academyName",
            "description": "<p>학원 이름</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>수업 이름</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "weekDay",
            "description": "<p>수업 진행 요일</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "time",
            "description": "<p>수업 진행 시간</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "totalDate",
            "description": "<p>수업 진행 총 기간</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "week",
            "description": "<p>수업 진행 주</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "studentNum",
            "description": "<p>수업 학생 수</p>"
          }
        ]
      }
    }
  },
  {
    "type": "delete",
    "url": "lecture/:lectureSeq",
    "title": "수업 삭제 처리",
    "name": "Delete_Lecture",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isExpired",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "delete",
    "url": "lecture/student/:lectureSeq/:userSeq",
    "title": "수업에서 학생 삭제",
    "name": "Delete_student_on_lecture_for_tutor",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "lecture",
    "title": "전체 수업 목록",
    "name": "Get_LectureList",
    "group": "Lecture",
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "lecture/recognition/:lectureSeq",
    "title": "수업 인증 요청 목록",
    "name": "Get_Lecture_recognition_request_list",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "lecture/student",
    "title": "학생 전용 수업 목록",
    "name": "Get_Student_LectureList",
    "group": "Lecture",
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Users unique ID.</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "lecture/student/:lectureSeq",
    "title": "수업별 학생 목록",
    "name": "Get_Student_LectureList",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>Users unique ID.</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "lecture/tutor",
    "title": "튜터용 학생의 수업 목록 조회",
    "name": "Get_student_lecture_list_for_tutor",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userSeq",
            "description": "<p>학생 시퀀스 번호</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "patch",
    "url": "lecture/:lectureSeq",
    "title": "수업 폐강 처리",
    "name": "Patch_Lecture_Expired",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isExpired",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "patch",
    "url": "lecture/student/:lectureSeq",
    "title": "수업 인증 확인",
    "name": "Patch_Request_Lecture_recognition",
    "group": "Lecture",
    "permission": [
      {
        "name": "tutor",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group tutor</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "studentSeq",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "isAccept",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "post",
    "url": "lecture/add/student",
    "title": "인증 요청하기",
    "name": "Request_Lecture_Recognition",
    "group": "Lecture",
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>유저 액세스 토큰</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "lectureSeq",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "notice/file",
    "title": "공지사항 첨부파일",
    "name": "Get_Notice_attached_file_list",
    "group": "Notice",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "noticeSeq",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/notice.js",
    "groupTitle": "Notice"
  },
  {
    "type": "get",
    "url": "notice",
    "title": "수업별 공지사항 리스트",
    "name": "Get_Notice_group_by_lecture",
    "group": "Notice",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "lectureSeq",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/notice.js",
    "groupTitle": "Notice"
  },
  {
    "type": "post",
    "url": "question",
    "title": "",
    "name": "Add_Question_item",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": ""
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "contents",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "get",
    "url": "question/file/:questionSeq",
    "title": "질문 첨부파일",
    "name": "GET_question_attached_file",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "get",
    "url": "answer/:questionSeq",
    "title": "답변 목록",
    "name": "Get_Answer_List_by_questionSeq",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "get",
    "url": "answer/file/:answerSeq",
    "title": "답변 목록",
    "name": "Get_Answer_List_by_questionSeq",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "get",
    "url": "question",
    "title": "질문 목록 (학생 : 자신이 한 질문, 튜터 : 모든 질문)",
    "name": "Get_QuestionList",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "post",
    "url": "editQuestion/:questionSeq",
    "title": "",
    "name": "Upload_image_file_to_question",
    "group": "QnA",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "File",
            "optional": false,
            "field": "file",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/qna.js",
    "groupTitle": "QnA"
  },
  {
    "type": "get",
    "url": "/file/question/attachedFile",
    "title": "질문 첨부파일",
    "name": "GET_question_attached_file",
    "group": "ServerFile",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/serverfile.js",
    "groupTitle": "ServerFile"
  },
  {
    "type": "get",
    "url": "/file/answer/attachedFile",
    "title": "답변 목록",
    "name": "Get_Answer_List_by_questionSeq",
    "group": "ServerFile",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/serverfile.js",
    "groupTitle": "ServerFile"
  },
  {
    "type": "",
    "url": "file/assignment/answer",
    "title": "과제 답안지 파일 확인",
    "name": "getAssignmentAnswer",
    "group": "ServerFile",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "Int",
            "description": "<p>assignmentSeq 과제 번호</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/serverfile.js",
    "groupTitle": "ServerFile"
  },
  {
    "type": "",
    "url": "file/assignment/ownsubmit",
    "title": "과제 제출된 파일 확인",
    "name": "getOwnAssignmentSubmitFiles",
    "group": "ServerFile",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "Int",
            "description": "<p>assignmentSeq 과제 번호</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/serverfile.js",
    "groupTitle": "ServerFile"
  },
  {
    "type": "",
    "url": "file/test/answer",
    "title": "테스트 답안지 파일 확인",
    "name": "getTestAnswer",
    "group": "ServerFile",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "optional": false,
            "field": "Int",
            "description": "<p>testSeq 테스 번호</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/serverfile.js",
    "groupTitle": "ServerFile"
  },
  {
    "type": "get",
    "url": "test/student",
    "title": "GetTestDetail for Student",
    "name": "Get_Test_detail_for_student",
    "group": "Test",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "student",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group student</p>"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": false,
            "field": "testAdmSeq",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "routes/contentRoute/test.js",
    "groupTitle": "Test"
  },
  {
    "type": "get",
    "url": "test/student/:lectureSeq",
    "title": "GetTestList by Lecture",
    "name": "Get_Test_list_group_by_lecture",
    "group": "Test",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "optional": false,
            "field": "x-access-token",
            "description": "<p>사용자 액세스 토큰</p>"
          }
        ]
      }
    },
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/test.js",
    "groupTitle": "Test"
  }
] });
