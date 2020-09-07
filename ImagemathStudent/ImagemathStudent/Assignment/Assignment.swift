//
//  Assignment.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

struct Assignment {
    let userSeq:Int
    let submitState:Int
    let assignmentSeq: Int
    let title: String
    let contents: String
    let postTime: Int
    let endTime: Int
    let lectureTime: Int
    let answerFiles: [ServerFile]
    let lectureName: String
}

extension Assignment{
    static func getAssignmentDummyData() -> [Assignment]{
        return [Assignment(userSeq: 0, submitState: 0, assignmentSeq: 0, title: "2주차", contents: ".", postTime: 100, endTime: 100, lectureTime: 100, answerFiles: ServerFile.getDummyData(), lectureName: "고3 fly 모의고사 나형 대치이강")]
    }
}
