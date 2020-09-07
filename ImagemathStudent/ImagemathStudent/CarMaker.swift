//
//  CarMaker.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//

struct CarMaker{
    let name: String
    let imageUrl: String
    let numberOfStore: Int
}

extension CarMaker{
    static func all() -> [CarMaker]{
        return [
            CarMaker(name: "기아자동차", imageUrl: "car1", numberOfStore: 2000),
            CarMaker(name: "현대자동차", imageUrl: "car2", numberOfStore: 2100),
            CarMaker(name: "쌍용자동차", imageUrl: "car3", numberOfStore: 2200)
        ]
    }
}
