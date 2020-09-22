//
//  QnADetailView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/16.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct QnADetailView: View {
    let question:Question;
    
    var body: some View {
        VStack{
            ZStack{
                HStack(alignment:.top){
                    VStack(alignment: .leading){
                        Text(question.title)
                        Rectangle().frame(width:50,height: 3).foregroundColor(Color.red)
                        Spacer().frame(height: 30)
                        Text(question.contents)
                    }
                    Spacer()
                    Text("아직 답변 대기중인 질문입니다.").font(.system(size:12)).foregroundColor(Color.gray)
                }.padding()
            }
                .overlay(RoundedRectangle(cornerRadius: 4).stroke().foregroundColor(Color("borderColor")))
                .background(Image(uiImage: #imageLiteral(resourceName: "box_content_msg")).resizable())
        }.navigationBarTitle("답변 확인하기")
    }
}

struct QnADetailView_Previews: PreviewProvider {
    static var previews: some View {
        QnADetailView(question: Question.getDummyData()[0])
    }
}
