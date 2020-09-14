//
//  RouterEnums.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/13.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum AssignmentRouter: APIRouteable{
    case getAssignment([String: String]?)
    var parameters: [String : String]?{
        switch self {
        case let .getAssignment(parameters):
            return parameters;
        }
    }
    
    var path: String{
        switch self {
        case .getAssignment:return "assignment/"
        }
    }
    
    var method: HTTPMethod{
        switch self{
        case .getAssignment: return .get
        }
    }
    
}
