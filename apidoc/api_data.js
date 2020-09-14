define({ "api": [
  {
    "type": "get",
    "url": "/lecture",
    "title": "전체 수업 목록",
    "name": "Get_LectureList",
    "group": "Lecture",
    "version": "0.0.0",
    "filename": "routes/contentRoute/lecture.js",
    "groupTitle": "Lecture"
  },
  {
    "type": "get",
    "url": "/lecture",
    "title": "학생 전용 수업 목록",
    "name": "Get_Student_LectureList",
    "group": "Lecture",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "x-access-token",
            "description": ""
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
    "url": "/",
    "title": "수업별 과제 리스트 출력",
    "name": "assignment",
    "group": "assignment",
    "permission": [
      {
        "name": "normal",
        "title": "User access only",
        "description": "<p>This optional description belong to to the group normal</p>"
      }
    ],
    "version": "0.0.0",
    "filename": "routes/contentRoute/assignment.js",
    "groupTitle": "assignment"
  }
] });
