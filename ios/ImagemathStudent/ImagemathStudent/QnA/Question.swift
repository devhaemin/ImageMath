//
//  Question.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/09.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation


struct Question: Codable{
    
    let questionSeq:Int
    let title:String
    let contents:String
    let postTime:Int
    let updateTime:Int
    let uploaderSeq:Int
    
}
extension Question{
    static func getQuestionList(completion: @escaping (Result<[Question],Error>) -> Void){
        let router = QnARouter.getQuestionList
        APIClient.perform(router, completion: completion)
    }
    static func postQuestion(title:String, contents:String, completion: @escaping (Result<Question,Error>) ->Void){
        let router = QnARouter.postQuestion(title: title, contents: contents)
        APIClient.perform(router, completion: completion)
    }
}
extension ServerFile{
    static func getQuestionFileList(questionSeq: Int, completion : @escaping (Result<[ServerFile],Error>) ->Void){
        let router = QnARouter.getQuestionFileList(questionSeq: questionSeq)
        APIClient.perform(router, completion: completion)
    }
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

struct Answer:Codable{
    let answerSeq:Int
    let questionSeq:Int
    let title:String
    let contents:String
    let postTime:Int
    let updateTime:Int
}
extension Answer{
    static func getAnswerList(questionSeq: Int, completion: @escaping (Result<[Answer],Error>) -> Void){
        let router = QnARouter.getAnswerList(questionSeq: questionSeq)
        APIClient.perform(router, completion: completion)
    }
}
extension ServerFile{
    static func getAnswerFileList(answerSeq: Int, completion : @escaping (Result<[ServerFile],Error>) ->Void){
        let router = QnARouter.getAnswerFileList(answerSeq: answerSeq)
        APIClient.perform(router, completion: completion)
    }
}

extension Answer{
    static func getDummyData()->[Answer]{
        return [
            Answer(answerSeq: 0, questionSeq: 0, title: "답변 테스트", contents: "답변 테스트 입니다.", postTime: 13, updateTime: 13),
            Answer(answerSeq: 1, questionSeq: 0, title: "답변 테스트", contents: "답변 테스트 입니다.", postTime: 13, updateTime: 13),
            Answer(answerSeq: 2, questionSeq: 0, title: "답변 테스트", contents: "답변 테스트 입니다.", postTime: 13, updateTime: 13)
        ]
    }
}
