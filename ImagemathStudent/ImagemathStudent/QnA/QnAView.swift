//
//  QnAView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/25.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct QnAView: View {
    @State var questions = Question.getDummyData();
    
    var body: some View {
        VStack(alignment:.trailing){
            NavigationLink(
                destination: QnAPostView(),
                label: {
                    Text("+질문하기").padding()
                })
            List(self.questions, id: \.questionSeq) { question in
                ZStack{
                    
                    QnACell(question: question)
                    NavigationLink(destination:
                                    QnADetailView(question: question))
                    {
                        EmptyView()
                    }.buttonStyle(PlainButtonStyle())
                }
            }.navigationBarTitle("", displayMode: .inline)
            .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                NavigationLink(destination:AlarmView()){
                    Image(uiImage: #imageLiteral(resourceName: "img_alarm"))
                        .resizable()
                        .frame(width:40, height: 40)
                }.buttonStyle(PlainButtonStyle())
                NavigationLink(destination:SettingView()){
                    Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                        .frame(width:40, height: 40)
                }.buttonStyle(PlainButtonStyle())
            })
            .onAppear(perform: {
                Question.getQuestionList { (response) in
                    do{
                        questions = try response.get()
                        questions.sort{
                            return $0.postTime > $1.postTime
                        }
                    }catch{
                        print(response)
                    }
                }
            })
        }
    }
}

struct QnAView_Previews: PreviewProvider {
    static var previews: some View {
        QnAView()
    }
}
