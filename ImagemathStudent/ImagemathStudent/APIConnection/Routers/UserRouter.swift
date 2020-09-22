//
//  UserRouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum UserRouter: APIRouteable{
    case tokenLogin
    case login(email: String, password: String)
    case register(user: User)
    var parameters: [String : String]?{
        switch self {
        case .tokenLogin:
            return nil;
        case .login(let email, let password):
            return ["email" : email, "password" : password]
        case .register(let user):
            return ["email":user.email!, "password":user.password!,
                    "name":user.name!, "birthday":user.birthday!,
                    "gender":String(user.gender!), "userType":user.userType!,
                    "phone":user.phone!, "schoolName":user.schoolName!,
                    "studentCode":user.studentCode!
            ]
        }
    }
    var path: String{
        switch self{
        case .tokenLogin:
            return "/auth/autologin"
        case .login:
            return "/auth/login"
        case .register:
            return "/auth/register"
        }
    }
    var method: HTTPMethod{
        switch self {
        case .tokenLogin:
            return .get
        case .login:
            return .post
        case .register:
            return .post
        }
    }
}
