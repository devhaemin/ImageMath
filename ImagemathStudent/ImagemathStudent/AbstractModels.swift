//
//  AbstractModels.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation


struct ServerFile{
    
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
