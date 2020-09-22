//
//  MainView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/16.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct MainView: View {
    
    
    let tests = Test.getDummyData();
    let questions = Question.getDummyData();
    
    @State var selectedView = 0
    
    @State var testAverageScore = 100;
    
    
    var body: some View{
        
        TabView(selection: $selectedView){
            
            //*********************************
            //            수강중인 강좌 탭
            //*********************************
            
            NavigationView{
               LectureView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("수강중인 강좌")
                    
            }.tag(0)
            
            
            //*********************************
            //            과제 탭
            //*********************************
            
            NavigationView{
                AssignmentView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("과제")
            }.tag(1)
            
            
            //*********************************
            //            테스트 탭
            //*********************************
            
            
            NavigationView{
                VStack{
                    HStack{
                        Spacer().frame(width:24)
                        Text("수업").font(.system(size: 18))
                        Spacer().frame(width:24)
                        Rectangle().frame(width:1, height: 20)
                        Spacer()
                        Button(action: {
                            
                        }) {
                            Text("TEST용")
                        }
                        Spacer().frame(width:14)
                    }.padding()
                    .overlay(RoundedRectangle(cornerRadius: 14)
                                .stroke()
                                .foregroundColor(Color("borderColor"))
                    ).padding()
                    HStack(spacing:0){
                        ZStack{
                            Image(uiImage: #imageLiteral(resourceName: "box_showstate")).resizable()
                                .frame(height: 100)
                            Text(String(tests[0].rank)+"등").font(.system(size: 24)).foregroundColor(Color.white).padding(.bottom)
                        }
                        ZStack{
                            Image(uiImage: #imageLiteral(resourceName: "box_showavgscore")).resizable()
                                .frame(height: 100)
                            Text(String(self.testAverageScore)+"점").font(.system(size: 24)).foregroundColor(Color.white).padding(.bottom)
                        }
                        ZStack{
                            Image(uiImage: #imageLiteral(resourceName: "box_showrecentscore")).resizable()
                                .frame(height: 100)
                            Text(String(tests[0].score)+"점").font(.system(size: 24)).foregroundColor(Color.white).padding(.bottom)
                        }
                    }.padding(.horizontal)
                    
                    List(self.tests, id: \.testAdmSeq) { test in
                        
                        ZStack{
                            
                            TestCell(test: test)
                            NavigationLink(destination:
                                            TestDetailView(test: test))
                            {
                                EmptyView()
                            }.buttonStyle(PlainButtonStyle())
                        }
                    }
                }
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
                    }.buttonStyle(PlainButtonStyle())
                })
                
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("테스트")
            }.tag(2)
            
            
            //*********************************
            //            QnA 탭
            //*********************************
            
            
            NavigationView{
                List(self.questions, id: \.questionSeq) { question in
                    ZStack{
                        
                        QnACell(question: question)
                        NavigationLink(destination:
                                        Text(question.title))
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
                
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("Q&A")
            }.tag(3)
            
        }.onAppear(){
            self.selectedView = 0
        }
        
    }
}

struct CheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        return HStack {
            Image(systemName: configuration.isOn ? "checkmark.square" : "square")
                .resizable()
                .frame(width: 22, height: 22)
                .onTapGesture { configuration.isOn.toggle() }
        }
    }
}


struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
