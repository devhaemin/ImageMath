//
//  File.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//
import Alamofire

struct Lecture: Codable{
    let lectureSeq: Int?
    let time: String?
    let name: String?
    let totalDate: String?
    let reqStudentCnt: Int?
    let studentNum: Int?
    let academyName: String?
    let academySeq: Int?
    let isExpired: String?
}

extension Lecture{
    static func getAllLectureList(completion : @escaping (Result<[Lecture],Error>)->Void){
        let router = LectureRouter.getAllLectureList
        APIClient.perform(router, completion: completion)
    }
    static func getMyLectureList(completion : @escaping (Result<[Lecture],Error>)->Void){
        let router = LectureRouter.getMyLectureList
        APIClient.perform(router, completion: completion)
    }
    static func requestAddLecture(lectureSeq: Int, completion : @escaping (Result<[Lecture],Error>)->Void){
        let router = LectureRouter.requestAddLecture(lectureSeq: lectureSeq)
        APIClient.perform(router, completion: completion)
    }
}

extension Lecture{
    static func getDummyData() -> [Lecture]{
        return [
            Lecture(lectureSeq: 0, time: "00:00~00:00", name: "수업을 선택해주세요.", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: 10, academyName: "", academySeq: 0, isExpired: "")
        ]
    }
    static func getPreviewDummyData() -> Lecture{
        return Lecture(lectureSeq: 0, time: "18:30~22:10", name: "Jump 나형", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: 10, academyName: "대치 이강학원", academySeq: 0 , isExpired: "false");
    }
}

struct Notice:Codable{
    let noticeSeq: Int?
    let title: String?
    let postTime: Int?
    let contents: String?
    let lectureSeq: Int?
}
extension Notice{
    static func getNoticeList(lectureSeq: Int, completion: @escaping (Result<[Notice],Error>)->Void){
        let router = LectureRouter.getNoticeList(lectureSeq: lectureSeq)
        APIClient.perform(router, completion: completion)
    }
}
extension ServerFile{
    static func getNoticeFiles(noticeSeq: Int, completion: @escaping (Result<[ServerFile], Error>)-> Void){
        let router = FileRouter.getNoticeAttachFile(noticeSeq: noticeSeq)
        APIClient.perform(router, completion: completion)
    }
}

extension Notice{
    static func getLectureNotice(lectureSeq:Int) -> [Notice]{
        return[
            Notice(noticeSeq: 0, title: "Test1", postTime: 1599483746100, contents: "테스트용 공지사항입니다.", lectureSeq: 0),
            Notice(noticeSeq: 1, title: "Test1", postTime: 1599483746100, contents: "테스트용 공지사항입니다.", lectureSeq: 0)
        ]
    }
}




