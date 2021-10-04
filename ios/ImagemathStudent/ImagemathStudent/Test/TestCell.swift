//
//  TestCell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct TestCell: View {
    
    let test: Test
    
    var body: some View {
        ZStack(alignment: .leading){
                            Image(uiImage: #imageLiteral(resourceName: "img_testinfo_on")).resizable().frame(width: 90, height: 90, alignment: .leading)
            Text(String(test.rank)+"등").font(.system(size: 18)).foregroundColor(Color.white).padding(.leading,32)
                HStack{
                    Spacer().frame(width: 70)
                    HStack{
                    Circle().frame(width: 12, height: 12).foregroundColor(Color("etoosColor"))
                        Text(test.title)
                        Spacer().frame(width: 24)
                        Rectangle().frame(width: 1, height: 40).foregroundColor(Color("borderColor"))
                        VStack(alignment: .leading){
                            HStack{
                                Text("배점 결과").frame(width: 70, alignment: .leading)
                                Text(":")
                                Text(String(test.score)+"점").foregroundColor(Color("etoosColor"))
                            }
                            HStack{
                            Text("등수").frame(width: 70, alignment: .leading)
                                Text(":")
                                Text(String(test.rank)+"등").foregroundColor(Color("etoosColor"))
                            }
                        }
                    }
                    .padding(.horizontal)
                        .padding(.vertical,20)
                        .background(Rectangle().foregroundColor(Color.white))
                        .overlay(RoundedRectangle(cornerRadius: 4).stroke().foregroundColor(Color("borderColor")))
                    .shadow(radius: 4)
                }
            }
        
    }
}

struct TestCell_Previews: PreviewProvider {
    static var previews: some View {
        TestCell(test: Test.getDummyData()[0])
    }
}
