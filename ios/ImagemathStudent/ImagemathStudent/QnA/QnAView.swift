//
//  QnAView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/25.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI
import SwiftUIRefresh

struct QnAView: View {
    @State var questions = Question.getDummyData();
    @State var isShowingRefresh = false;
    
    
    var body: some View {
        VStack(alignment:.trailing){
            NavigationLink(
                destination: QnAPostView(),
                label: {
                    Text("+질문하기").padding()
                })
            if(questions.isEmpty){
                VStack{
                    Spacer()
                    HStack{
                        Spacer()
                        Text("질문한 내용이 없습니다.\n우측 상단 버튼을 눌러 질문해주세요!")
                        Spacer()
                    }
                    Spacer()
                }
            }
            ScrollView{
                VStack{
                ForEach(self.questions, id: \.questionSeq) { question in
                    
                        
                        NavigationLink(destination:
                                        QnADetailView(question: question))
                        {
                            QnACell(question: question)
                        }.buttonStyle(PlainButtonStyle())
                    
                }
                }
            }
            .pullToRefresh(isShowing: $isShowingRefresh, onRefresh: {
                refresh();
            })
            .navigationBarTitle("", displayMode: .inline)
            .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                NavigationLink(destination:AlarmView()){
                    Image(uiImage: #imageLiteral(resourceName: "img_alarm"))
                        .resizable()
                        .frame(width:40, height: 40)
                }.buttonStyle(PlainButtonStyle())
                NavigationLink(destination:SettingView()){
                    Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                        .frame(width:40, height: 40)
                }.buttonStyle(FlatLinkStyle())
            })
            
            .onAppear(perform: {
                refresh()
            })
        }
    }
    
    func refresh(){
        Question.getQuestionList { (response) in
            do{
                questions = try response.get()
                questions.sort{
                    return $0.postTime > $1.postTime
                }
                isShowingRefresh = false;
            }catch{
                print(response)
            }
        }
    }
}

struct QnAView_Previews: PreviewProvider {
    static var previews: some View {
        QnAView()
    }
}
struct FlatLinkStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
    }
}
