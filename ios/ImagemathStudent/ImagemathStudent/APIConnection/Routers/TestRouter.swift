//
//  TestRouter.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation
import Alamofire

enum TestRouter : APIRouteable{
    case getTestList(lectureSeq:Int)
    case getTestDetail(testSeq: Int)
    
    var path: String{
        switch self{
        case .getTestList(let lectureSeq):
            return "/test/student/"+String(lectureSeq)
        case .getTestDetail:
            return "/test/student"
        }
    }
    
    var method: HTTPMethod{
        switch self {
        case .getTestList:
            return .get
        case .getTestDetail:
            return .get
        }
    }
    
    var parameters: [String : String]?{
        switch self {
        case .getTestList:
            return nil
        case .getTestDetail(let testSeq):
            return ["testSeq":String(testSeq)]
        }
    }
    
    
}
