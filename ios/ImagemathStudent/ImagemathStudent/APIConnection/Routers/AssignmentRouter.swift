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
    case getAssignmentList
    case getAssignmentDetail(assignmentSeq:Int)
    case getStudentAssignmentList
    
    
    var parameters: [String : String]?{
        switch self {
        case .getAssignmentList:
            return nil
        case .getAssignmentDetail:
            return nil
        case .getStudentAssignmentList:
            return nil
        }
    }
    
    var path: String{
        switch self {
        case .getAssignmentList:
            return "/assignment"
        case .getAssignmentDetail(let assignmentSeq):
            return "/assignment/"+String(assignmentSeq)
        case .getStudentAssignmentList:
            return "/assignment/student"
        }
    }
    
    var method: HTTPMethod{
        switch self{
        case .getAssignmentList:
            return .get
        case .getAssignmentDetail:
            return .get
        case .getStudentAssignmentList:
            return .get
        }
    }
}
