//
//  Test.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation

struct Test :Codable{
    let testSeq:Int
    let title:String
    let postTime:Int
    let endTime:Int
    let lectureTime:Int
    let lectureName:String
    let lectureSeq:Int
    let averageScore:Int
    let contents:String
    let score:Int
    let rank:Int
    let testAdmSeq:Int
}
extension Test{
    static func getTestList(lectureSeq: Int, completion: @escaping (Result<[Test],Error>) -> Void){
        let router = TestRouter.getTestList(lectureSeq: lectureSeq)
        APIClient.perform(router, completion: completion)
    }
    static func getTestDetail(testSeq: Int, completion: @escaping (Result<[Test],Error>) -> Void){
        let router = TestRouter.getTestDetail(testSeq: testSeq)
        APIClient.perform(router, completion: completion)
    }
}

extension Test{
    static func getDummyData() -> [Test]{
        return [
            Test(testSeq: 0, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, lectureName: "고3 모의고사", lectureSeq: 0, averageScore: 100, contents: "Test 더미 데이터 1", score: 100, rank: 1, testAdmSeq: 0),
            Test(testSeq: 1, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, lectureName: "고3 모의고사", lectureSeq: 0, averageScore: 100, contents: "Test 더미 데이터 2", score: 100, rank: 1, testAdmSeq: 1),
            Test(testSeq: 2, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, lectureName: "고3 모의고사", lectureSeq: 0, averageScore: 100, contents: "Test 더미 데이터 3", score: 100, rank: 1, testAdmSeq: 2)
        ]
    }
}
