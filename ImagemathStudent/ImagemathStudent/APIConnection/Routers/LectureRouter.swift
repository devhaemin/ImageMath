//
//  LectureRouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum LectureRouter : APIRouteable{
    
    case requestAddLecture(lectureSeq: Int)
    case getAllLectureList
    case getMyLectureList
    case getNoticeList(lectureSeq: Int)
    case getNoticeFiles(noticeSeq: Int)
    
    var path: String{
        switch self {
        case .requestAddLecture:
            return "/lecture/add/student"
        case .getAllLectureList:
            return "/lecture"
        case .getMyLectureList:
            return "/lecture/student"
        case .getNoticeList:
            return "/notice"
        case .getNoticeFiles:
            return "/notice/file"
        }
        
    }
    
    var method: HTTPMethod{
        switch self{
        case .requestAddLecture:
            return .post
        case .getAllLectureList:
            return .get
        case .getMyLectureList:
            return .get
        case .getNoticeList:
            return .get
        case .getNoticeFiles:
            return .get
        }
    }
    
    var parameters: [String : String]?{
        switch self{
        case .requestAddLecture(let lectureSeq):
            return ["lectureSeq":String(lectureSeq)]
        case .getAllLectureList:
            return nil
        case .getMyLectureList:
            return nil
        case .getNoticeList(let lectureSeq):
            return ["lectureSeq":String(lectureSeq)]
        case .getNoticeFiles(let noticeSeq):
            return ["noticeSeq": String(noticeSeq)]
        }
    }
    
}
