//
//  File.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//
import Alamofire

struct Lecture{
    let lectureSeq: Int
    let time: String
    let name: String
    let totalDate: String
    let reqStudentCnt: Int
    let studentNum: String
    let academyName: String
    let academySeq: String
    let isExpired: Bool
}

extension Lecture{
    static func getDummyData() -> [Lecture]{
        return [
            Lecture(lectureSeq: 0, time: "18:30~22:10", name: "Jump 나형", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: "10", academyName: "대치 이강학원", academySeq: "0", isExpired: false),
            Lecture(lectureSeq: 0, time: "18:30~22:10", name: "Jump 가형", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: "10", academyName: "대치 이강학원", academySeq: "0", isExpired: false),
            Lecture(lectureSeq: 0, time: "18:30~22:10", name: "Test용", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: "10", academyName: "대치 이강학원", academySeq: "0", isExpired: false)
        ]
    }
    static func getPreviewDummyData() -> Lecture{
        return Lecture(lectureSeq: 0, time: "18:30~22:10", name: "Jump 나형", totalDate: "2020.06.20~2020.08.30", reqStudentCnt: 0, studentNum: "10", academyName: "대치 이강학원", academySeq: "0", isExpired: false);
    }
    func getNoticeInfo() -> [Notice]{
        return Notice.getLectureNotice(lectureSeq: self.lectureSeq);
    }
}

struct Notice{
    let noticeSeq: Int
    let title: String
    let postTime: Int
    let contents: String
    let lectureSeq: Int
    let files: [ServerFile]
}

extension Notice{
    static func getLectureNotice(lectureSeq:Int) -> [Notice]{
        return[
            Notice(noticeSeq: 0, title: "Test1", postTime: 1599483746100, contents: "테스트용 공지사항입니다.", lectureSeq: 0, files: ServerFile.getDummyData()),
            Notice(noticeSeq: 1, title: "Test1", postTime: 1599483746100, contents: "테스트용 공지사항입니다.", lectureSeq: 0, files: ServerFile.getDummyData())
        ]
    }
}




