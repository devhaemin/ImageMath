//
//  ServerFile.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/27.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

struct ServerFile : Codable{
    
    let fileSeq: Int?
    let fileName: String?
    let fileType: String?
    let postSeq: Int?
    let uploadTime: Int?
    let userSeq: Int?
    let fileUrl: String?
}
extension ServerFile{
    
    static func getDummyData() -> [ServerFile]{
        return [
            ServerFile(fileSeq: 0, fileName: "test_1.png", fileType: "normal", postSeq: 0, uploadTime: 1599483746100, userSeq: 0, fileUrl:"https://imagemath.s3.ap-northeast-2.amazonaws.com/1601196429361_4.png"),
            ServerFile(fileSeq: 1, fileName: "test_2.png", fileType: "normal", postSeq: 0, uploadTime: 1599483746100, userSeq: 0,fileUrl: "https://imagemath.s3.ap-northeast-2.amazonaws.com/1601196429361_4.png")
        ]
    }
}
extension ServerFile{
    static func getAssignmentSubmitFiles(assignmentSeq:Int, completion: @escaping (Result<[ServerFile], Error>)->Void){
        let router = FileRouter.getAssignmentSubmitFile(assignmentSeq: assignmentSeq)
        APIClient.perform(router, completion: completion)
    }
}
extension ServerFile{
    static func getAssignmentAnswerFiles(assignmentSeq: Int, completion: @escaping (Result<[ServerFile], Error>)->Void){
        let router = FileRouter.getAssignmentAnswerFile(assignmentSeq: assignmentSeq)
        APIClient.perform(router, completion: completion)
    }
    static func getTestAnswerFiles(testSeq: Int, completion: @escaping (Result<[ServerFile], Error>)->Void){
        let router = FileRouter.getTestAnswerFile(testSeq: testSeq)
        APIClient.perform(router, completion: completion)
    }
}
extension ServerFile{
    static func getQuestionAttachFile(questionSeq: Int, completion: @escaping (Result<[ServerFile],Error>) -> Void){
        let router = FileRouter.getQuestionAttachFile(questionSeq: questionSeq)
        APIClient.perform(router, completion: completion)
    }
    static func getAnswerAttachFile(answerSeq: Int, completion: @escaping (Result<[ServerFile], Error>)-> Void){
        let router = FileRouter.getAnswerAttachFile(answerSeq: answerSeq)
        APIClient.perform(router, completion: completion)
    }
    static func getNoticeAttachFile(noticeSeq: Int, completion: @escaping (Result<[ServerFile], Error>) -> Void){
        let router = FileRouter.getNoticeAttachFile(noticeSeq: noticeSeq)
        APIClient.perform(router, completion: completion)
    }
}
