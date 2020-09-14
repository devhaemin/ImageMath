//
//  Test.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation

struct Test {
    let testSeq:Int
    let title:String
    let postTime:Int
    let endTime:Int
    let lectureTime:Int
    let solutionFileUrl:String
    let lectureName:String
    let lectureSeq:Int
    let answerFiles:[ServerFile]
    let averageScore:Int
    let contents:String
    let score:Int
    let rank:Int
    let testAdmSeq:Int
}

extension Test{
    static func getDummyData() -> [Test]{
        return [
            Test(testSeq: 0, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, solutionFileUrl: "http://naver.com", lectureName: "고3 모의고사", lectureSeq: 0, answerFiles: ServerFile.getDummyData(), averageScore: 100, contents: "Test 더미 데이터 1", score: 100, rank: 1, testAdmSeq: 0),
            Test(testSeq: 1, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, solutionFileUrl: "http://naver.com", lectureName: "고3 모의고사", lectureSeq: 0, answerFiles: ServerFile.getDummyData(), averageScore: 100, contents: "Test 더미 데이터 2", score: 100, rank: 1, testAdmSeq: 1),
            Test(testSeq: 2, title: "Test", postTime: 1515, endTime: 1515, lectureTime: 145145, solutionFileUrl: "http://naver.com", lectureName: "고3 모의고사", lectureSeq: 0, answerFiles: ServerFile.getDummyData(), averageScore: 100, contents: "Test 더미 데이터 3", score: 100, rank: 1, testAdmSeq: 2)
        ]
    }
}
