//
//  Question.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/09.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation


struct Question{
    let questionSeq:Int
    let title:String
    let contents:String
    let postTime:Int
    let updateTime:Int
    let uploaderSeq:Int
    
}
extension Question{
    
    static func getDummyData()->[Question]{
        return [
            Question(questionSeq: 0, title: "이미지 포함 테스트", contents: "테스트", postTime: 1515, updateTime: 1515, uploaderSeq: 0),
            Question(questionSeq: 1, title: "이미지 포함 테스트", contents: "테스트", postTime: 1515, updateTime: 1515, uploaderSeq: 0),
            Question(questionSeq: 2, title: "이미지 포함 테스트", contents: "테스트", postTime: 1515, updateTime: 1515, uploaderSeq: 0),
            Question(questionSeq: 3, title: "이미지 포함 테스트", contents: "테스트", postTime: 1515, updateTime: 1515, uploaderSeq: 0),
            Question(questionSeq: 4, title: "이미지 포함 테스트", contents: "테스트", postTime: 1515, updateTime: 1515, uploaderSeq: 0)
        ]
    }
}
