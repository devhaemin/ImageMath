//
//  Assignment.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//
import Foundation
import Alamofire

struct Assignment : Codable{
    let userSeq:Int
    let submitState:Int
    let assignmentSeq: Int
    let title: String
    let contents: String
    let postTime: Int
    let endTime: Int
    let lectureTime: Int
    let lectureName: String
}
extension Assignment{
    static func getAssignmentList(completion: @escaping (Result<[Assignment],Error>)->Void){
        let router = AssignmentRouter.getAssignmentList
        APIClient.perform(router, completion: completion)
    }
    static func getAssignmentDetail(assignmentSeq:Int, completion:@escaping (Result<[Assignment],Error>)->Void){
        let router = AssignmentRouter.getAssignmentDetail(assignmentSeq: assignmentSeq)
        APIClient.perform(router, completion: completion)
    }
    static func getStudentAssignmentList(completion: @escaping (Result<[Assignment],Error>)->Void){
        let router = AssignmentRouter.getStudentAssignmentList
        APIClient.perform(router, completion: completion)
    }
    
}

extension Assignment{
    static func getAssignmentDummyData() -> [Assignment]{
        return [Assignment(userSeq: 0, submitState: 0, assignmentSeq: 0, title: "2주차", contents: ".", postTime: 100, endTime: 1599476400830, lectureTime: 100, lectureName: "고3 fly 모의고사 나형 대치이강"),
                Assignment(userSeq: 0, submitState: 0, assignmentSeq: 0, title: "2주차", contents: ".", postTime: 100, endTime: 1599476410830, lectureTime: 100, lectureName: "고3 fly 모의고사 나형 대치이강"),
                Assignment(userSeq: 0, submitState: 0, assignmentSeq: 0, title: "2주차", contents: ".", postTime: 100, endTime: 1599476500830, lectureTime: 100, lectureName: "고3 fly 모의고사 나형 대치이강")]
    }
}
extension Assignment{
    static func getAnswerFiles() -> [ServerFile]{
        return ServerFile.getDummyData()
    }
}
