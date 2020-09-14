//
//  ContentView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/05.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    let carMakers = CarMaker.all();
    let lectures = Lecture.getDummyData();
    let assignments = Assignment.getAssignmentDummyData();
    let tests = Test.getDummyData();
    @State var selectedView = 0
    @State var isExceptOn = false
    @State var showingSheet = false
    @State var testAverageScore = 100;
    
    
    var body: some View{
        
        TabView(selection: $selectedView){
            
            //*********************************
            //            수강중인 강좌 탭
            //*********************************
            
            NavigationView{
                VStack{
                    HStack{
                        Toggle(isOn: $isExceptOn){
                            Text("")
                        }
                        .toggleStyle(CheckboxToggleStyle())
                        .labelsHidden()
                        Text("종료된 강좌 제외")
                        Spacer()
                        Button(action: {
                            self.showingSheet = true
                        }) {
                            Text("+추가하기")
                        }.actionSheet(isPresented: $showingSheet) {
                            ActionSheet(title: Text("수업 선택하기"), message: Text("승인 요청할 수업을 선택해주세요."), buttons: [.default(Text("고3 fly 모의고사")), .cancel(Text("Cancel"))])
                        }
                    }.padding(.top)
                        .padding(.horizontal)
                    List(self.lectures, id: \.lectureSeq) { lecture in
                        ZStack {
                            LectureCell(lecture: lecture, notices: Notice.getLectureNotice(lectureSeq: lecture.lectureSeq))
                            NavigationLink(destination: NoticeView(notices: Notice.getLectureNotice(lectureSeq: lecture.lectureSeq))) {
                                EmptyView()
                            }.buttonStyle(PlainButtonStyle())
                        }
                    }
                }
                .navigationBarTitle("", displayMode: .inline)
                .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                    Image(uiImage: #imageLiteral(resourceName: "img_alarm")).resizable()
                        .frame(width:40, height: 40)
                    Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                        .frame(width:40, height: 40)
                })
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("수강중인 강좌")
            }.tag(0)
            
            
            //*********************************
            //            과제 탭
            //*********************************
            
            NavigationView{
                VStack{
                    HStack{
                        Spacer()
                        Circle().frame(width:12,height: 12)
                        Text("미제출")
                        Circle().frame(width:12,height: 12)
                            .foregroundColor(Color.yellow)
                        Text("제출")
                        Circle().frame(width:12,height: 12)
                            .foregroundColor(Color.red)
                        Text("불성실")
                        Circle().frame(width:12,height: 12)
                            .foregroundColor(Color("etoosColor"))
                        Text("통과")
                        
                    }.padding(.trailing)
                        .padding(.top)
                    Spacer().frame(height:14)
                    List(self.assignments, id: \.assignmentSeq) { assignment in
                        AssignmentCell(dailyAssignments: self.assignments)
                    }
                }.navigationBarTitle("", displayMode: .inline)
                    .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                        Image(uiImage: #imageLiteral(resourceName: "img_alarm")).resizable()
                            .frame(width:40, height: 40)
                        Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                            .frame(width:40, height: 40)
                    })
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
                            Text(test.title))
                        {
                            EmptyView()
                            }.buttonStyle(PlainButtonStyle())
                        }
                    }
                }
                    
                .navigationBarTitle("", displayMode: .inline)
                .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                    Image(uiImage: #imageLiteral(resourceName: "img_alarm")).resizable()
                        .frame(width:40, height: 40)
                    Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                        .frame(width:40, height: 40)
                })
                
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("테스트")
            }.tag(2)
            
            
            //*********************************
            //            QnA 탭
            //*********************************
            
            
            NavigationView{
                List(self.carMakers, id: \.name) { carMaker in
                    
                    NavigationLink(destination:
                        Text(carMaker.name))
                    {
                        CarMakerCell(carMaker: carMaker);
                    }
                }.navigationBarTitle("", displayMode: .inline)
                    .navigationBarItems(leading: Image(uiImage: #imageLiteral(resourceName: "logo_img_small")).resizable().frame(width:140,height: 50), trailing: HStack{
                        Image(uiImage: #imageLiteral(resourceName: "img_alarm")).resizable()
                            .frame(width:40, height: 40)
                        Image(uiImage: #imageLiteral(resourceName: "img_setting")).resizable()
                            .frame(width:40, height: 40)
                    })
                
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("Q&A")
            }.tag(3)
            
        }.onAppear(){
            self.selectedView = 1
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

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct CarMakerCell: View {
    let carMaker: CarMaker
    
    var body: some View{
        HStack{
            Image(carMaker.imageUrl)
                .resizable()
                .frame(width: 100, height:  100)
                .cornerRadius(16)
            
            VStack(alignment: .leading){
                Text(carMaker.name).font(.largeTitle)
                Text("\(carMaker.numberOfStore) 지점")
            }
        }
    }
}
