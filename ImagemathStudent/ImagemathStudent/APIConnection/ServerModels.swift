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
}

struct ServerFile : Codable{
    
    let fileSeq: Int
    let fileName: String
    let fileType: String
    let postSeq: Int
    let uploadTime: Int
    let userSeq: Int
    
}
extension ServerFile{
    
    static func getDummyData() -> [ServerFile]{
        return [
            ServerFile(fileSeq: 0, fileName: "test_1.png", fileType: "normal", postSeq: 0, uploadTime: 1599483746100, userSeq: 0),
                ServerFile(fileSeq: 1, fileName: "test_2.png", fileType: "normal", postSeq: 0, uploadTime: 1599483746100, userSeq: 0)
        ]
    }
}
