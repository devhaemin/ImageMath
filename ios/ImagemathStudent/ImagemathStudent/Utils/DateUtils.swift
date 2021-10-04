//
//  DateUtils.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/28.
//  Copyright © 2020 정해민. All rights reserved.
//

import Foundation

struct DateUtils {
    static func getRelativeTimeString(unixtime : Int) -> String{
        let currentDate = Date().timeIntervalSince1970 * 1000
        let distance = Int(currentDate) - unixtime
        
        switch(distance){
        case 0..<6000:
            return String(distance / 1000)+"초 전"
        case (6000)..<3600000:
            return String(distance / (3600000) ) + "분 전"
        case (3600000)..<24*3600000 :
            return String(distance / (3600000) ) + "시간 전"
        case (24*3600000)..<(7*24*3600000):
            return String(distance / (24*3600000)) + "일 전"
        default:
            let date = Date(timeIntervalSince1970: TimeInterval(unixtime / 1000))
            let dateFormatter = DateFormatter()
            dateFormatter.timeZone = TimeZone(abbreviation: "GMT+9") //Set timezone that you want
            dateFormatter.locale = Locale(identifier: "ko-KR")
            dateFormatter.dateFormat = "YYYY.MM.dd"
            let strDate = dateFormatter.string(from: date)
            return strDate
        }
        
    }
}
func getTimeDate(date : Int) -> String {
    let date = Date(timeIntervalSince1970: TimeInterval(date*(3600*24)))
    let dateFormatter = DateFormatter()
    dateFormatter.timeZone = TimeZone(abbreviation: "GMT+9") //Set timezone that you want
    dateFormatter.locale = Locale(identifier: "ko-KR")
    dateFormatter.dateFormat = "MM. dd E"
    let strDate = dateFormatter.string(from: date)
    return strDate
}
