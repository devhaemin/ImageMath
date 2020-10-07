//
//  AlarmRouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/29.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum AlarmRouter:APIRouteable{
    case getAlarmList
    
    var path: String{
        switch self {
        case .getAlarmList:
            return "/alarm"
        }
    }
    
    var method: HTTPMethod{
        switch self{
        case .getAlarmList:
            return .get
        }
    }
    
    var parameters: [String : String]?{
        switch self {
        case .getAlarmList:
            return nil;
        }
    }
    
    
    
}
