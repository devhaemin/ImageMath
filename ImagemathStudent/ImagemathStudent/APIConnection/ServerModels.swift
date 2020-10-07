//
//  AbstractModels.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation

struct User : Codable{
    let email:String?
    let password:String?
    let userSeq:Int?
    let name:String?
    let birthday:String?
    let accessToken:String?
    let fcmToken:String?
    let gender:Int?
    let userType:String?
    let phone:String?
    let studentCode:String?
    let schoolName:String?
    let registerTime:Int?
}

struct DefaultServerModel: Decodable{
    
}
extension User{
    
    static func emailLogin(email:String, password:String, completion: @escaping (Result<User, Error>) -> Void){
        let router = UserRouter.login(email: email, password: password)
        APIClient.perform(router, completion: completion)
    }
}
extension User{
    static func tokenLogin(completion: @escaping (Result<User,Error>) -> Void){
        let router = UserRouter.tokenLogin
        APIClient.perform(router, completion: completion)
    }
    static func sendPushToken(fcmToken:String, completion: @escaping (Result<DefaultServerModel, Error>)->Void){
        let router = UserRouter.sendPushToken(fcmToken: fcmToken)
        APIClient.perform(router, completion: completion)
    }
}

struct Alarm : Codable{
    let alarmSeq:Int
    let title:String?
    let content:String?
    let type:String?
    let postTime:Int?
    
}
extension Alarm{
    static func getAlarmList(completion: @escaping (Result<[Alarm],Error>)->Void){
        let router = AlarmRouter.getAlarmList
        APIClient.perform(router, completion: completion)
    }
}
extension Alarm{
    static func getDummyData()->[Alarm]{
        return [Alarm(alarmSeq: 0, title: "답변이 등록되었습니다.", content: "Test에 대한 답변이 등록되었습니다.", type: "lol", postTime: 1599483746100)]
    }
}
