//
//  LectureView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct LectureView: View {
    
    @State var isExceptOn = false
    @State var showingSheet = false
    @State var lectures = Lecture.getDummyData();
    
    var body: some View {
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
                    LectureCell(lecture: lecture, notices: Notice.getLectureNotice(lectureSeq: lecture.lectureSeq!))
                    NavigationLink(destination: NoticeView(notices: Notice.getLectureNotice(lectureSeq: lecture.lectureSeq!), lectureName: "고3 대치이강 나형 모의고사")) {
                        EmptyView()
                    }.buttonStyle(PlainButtonStyle())
                }
            }
        }.onAppear(perform: {
            Lecture.getMyLectureList { (response) in
                do{
                    lectures = try response.get()
                }catch{
                    print(response)
                }
            }
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
            }.buttonStyle(PlainButtonStyle())
            
        })
    }
}

struct LectureView_Previews: PreviewProvider {
    static var previews: some View {
        LectureView()
    }
}
