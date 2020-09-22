//
//  QnACell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/09.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct QnACell: View {
    
    let question:Question
    
    var body: some View {
        ZStack{
            
            HStack(alignment:.top){
                VStack(alignment: .leading){
                    Text(question.title)
                    Rectangle().frame(width:50,height: 3).foregroundColor(Color.red)
                    Spacer()
                    Text(question.contents)
                }
                Spacer()
                Text("아직 답변 대기중인 질문입니다.").font(.system(size:12)).foregroundColor(Color.gray)
            }.padding()
        }.frame(height:210)
            .overlay(RoundedRectangle(cornerRadius: 4).stroke().foregroundColor(Color("borderColor")))
            .background(Image("box_content_msg").frame(maxWidth: .infinity)
                .fixedSize(horizontal: true, vertical: false).aspectRatio(contentMode: .fit))
            
    }
}

struct QnACell_Previews: PreviewProvider {
    static var previews: some View {
        QnACell(question: Question.getDummyData()[0])
    }
}
