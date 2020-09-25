//
//  TestView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/25.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct TestView: View {
    
    @State var tests = Test.getDummyData()
    @State var testAverageScore = 100
    @State var selectLectureVisible = true
    @State var myLectures = [Lecture]()
    @State var currentLecture = Lecture.getDummyData()[0]
    
    var body: some View {
        VStack{
            HStack{
                Spacer().frame(width:24)
                Text("수업").font(.system(size: 18))
                Spacer().frame(width:24)
                Rectangle().frame(width:1, height: 20)
                Spacer()
                Button(action: {
                    selectLectureVisible = true;
                }) {
                    Text(currentLecture.name!)
                }
                Spacer().frame(width:14)
            }.padding()
            .overlay(RoundedRectangle(cornerRadius: 14)
                        .stroke()
                        .foregroundColor(Color("borderColor"))
            ).padding()
            
            if(tests.count != 0){
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
                List{
                    ForEach(self.tests, id: \.testAdmSeq){ test in
                        ZStack{
                            
                            TestCell(test: test)
                            NavigationLink(destination:
                                            TestDetailView(test: test))
                            {
                                EmptyView()
                            }.buttonStyle(PlainButtonStyle())
                        }.listRowInsets(EdgeInsets())
                        .background(Color.white)
                        .padding(.vertical, 8)
                    }
                }
            }else{
                Text("등록된 시험 결과가 없습니다.\n수업을 선택해주세요.")
            }
        }
        
        .actionSheet(isPresented: $selectLectureVisible) {
            var buttons: [ActionSheet.Button] = myLectures.map {
                let lecture = $0
                return ActionSheet.Button.default(Text($0.name!), action: {
                    currentLecture = lecture
                    refresh()
                })
            }
            buttons.append(ActionSheet.Button.cancel(Text("취소")))
            return ActionSheet(title: Text("수업 선택하기"), message: Text("승인 요청할 수업을 선택해주세요."), buttons:buttons
            )
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
        .onAppear(perform: {
            Lecture.getMyLectureList { (response) in
                do{
                    myLectures = try response.get()
                }catch{
                    print(response)
                }
            }
            refresh()
        })
        
    }
}
extension TestView{
    func refresh() {
        Test.getTestList(lectureSeq: currentLecture.lectureSeq!) { (response) in
            do{
                tests = try response.get()
                var sumScore = 0
                var numTest = 0
                for test in tests{
                    numTest += 1
                    sumScore += test.score
                }
                if(numTest != 0){
                    testAverageScore = sumScore / numTest
                }
            }catch{
                print(response)
            }
        }
    }
}

struct TestView_Previews: PreviewProvider {
    static var previews: some View {
        TestView()
    }
}
