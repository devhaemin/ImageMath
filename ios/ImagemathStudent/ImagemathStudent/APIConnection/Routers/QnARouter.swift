//
//  QnARouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum QnARouter:APIRouteable{
    
    case getQuestionList
    case getQuestionFileList(questionSeq: Int)
    case getAnswerList(questionSeq: Int)
    case getAnswerFileList(answerSeq: Int)
    case postQuestion(title: String, contents: String)
    
    var path: String{
        switch self{
        case .getQuestionList:
            return "/qna/question"
        case .getQuestionFileList(let questionSeq):
            return "/qna/question/file/"+String(questionSeq)
        case .getAnswerList(let questionSeq):
            return "/qna/answer/"+String(questionSeq)
        case .getAnswerFileList(let answerSeq):
            return "/qna/answer/file/"+String(answerSeq)
        case .postQuestion:
            return "/qna/question"
        }
    }
    
    var method: HTTPMethod{
        switch self{
        case .getQuestionList:
            return .get
        case .getQuestionFileList:
            return .get
        case .getAnswerList:
            return .get
        case .getAnswerFileList:
            return .get
        case .postQuestion:
            return .post
        }
    }
    
    var parameters: [String : String]?{
        switch self {
        case .getQuestionList:
            return nil
        case .getQuestionFileList:
            return nil
        case .getAnswerList:
            return nil
        case .getAnswerFileList:
            return nil
        case .postQuestion(let title, let contents):
            return ["title":title,"contents":contents]
        }
    }
    
    
}
