//
//  FileRouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/27.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum FileRouter:APIRouteable{
    case getAssignmentSubmitFile(assignmentSeq: Int)
    case getAssignmentAnswerFile(assignmentSeq: Int)
    case getTestAnswerFile(testSeq: Int)
    case getAnswerAttachFile(answerSeq: Int)
    case getQuestionAttachFile(questionSeq: Int)
    case getNoticeAttachFile(noticeSeq: Int)
    
    var path: String{
        switch self {
        case .getAssignmentSubmitFile:
            return "/file/assignment/ownsubmit"
        case .getAssignmentAnswerFile:
            return "/file/assignment/answer"
        case .getTestAnswerFile:
            return "/file/test/answer"
        case .getAnswerAttachFile:
            return "/file/answer/attachedFile"
        case .getQuestionAttachFile:
            return "/file/question/attachedFile"
        case .getNoticeAttachFile:
            return "/file/notice/attachedFile"
        }
    }
    
    var method: HTTPMethod{
        switch self {
        case .getAssignmentSubmitFile:
            return .get
        case .getAssignmentAnswerFile:
            return .get
        case .getTestAnswerFile:
            return .get
        case .getAnswerAttachFile:
            return .get
        case .getQuestionAttachFile:
            return .get
        case .getNoticeAttachFile:
            return .get
        }
    }
    
    var parameters: [String : String]?{
        switch self {
        case .getAssignmentSubmitFile(let assignmentSeq):
            return ["assignmentSeq":String(assignmentSeq)]
        case .getAssignmentAnswerFile(let assignmentSeq):
            return ["assignmentSeq":String(assignmentSeq)]
        case .getTestAnswerFile(let testSeq):
            return ["testSeq": String(testSeq)]
        case .getAnswerAttachFile(let answerSeq):
            return ["answerSeq": String(answerSeq)]
        case .getQuestionAttachFile(let questionSeq):
            return ["questionSeq" : String(questionSeq)]
        case .getNoticeAttachFile(let noticeSeq):
            return ["noticeSeq": String(noticeSeq)]
        }
    }
}
