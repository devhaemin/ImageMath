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
    @State var allLectures = Lecture.getDummyData();
    
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
                    
                    var buttons: [ActionSheet.Button] = allLectures.map {
                        let lectureSeq = $0.lectureSeq!
                        return ActionSheet.Button.default(Text($0.name!), action: {
                            requestLecture(lectureSeq: lectureSeq)
                        })
                    }
                    buttons.append(ActionSheet.Button.cancel(Text("취소")))
                    return ActionSheet(title: Text("수업 선택하기"), message: Text("승인 요청할 수업을 선택해주세요."), buttons:buttons
                    )
                }
            }.padding(.top)
            .padding(.horizontal)
            if(lectures.count != 0){
            List {
                ForEach(self.lectures, id: \.lectureSeq){ lecture in
                    ZStack {
                        LectureCell(lecture: lecture)
                        NavigationLink(destination: NoticeView(lecture: lecture)) {
                            EmptyView()
                        }.buttonStyle(PlainButtonStyle())
                    }
                    .listRowInsets(EdgeInsets())
                    .padding(.vertical,8)
                    .background(Color.white)
                }
            }
            }else{
                Spacer()
                Text("등록된 수업이 없습니다.\n우측 상단의 추가하기 버튼을 눌러 추가해주세요.")
                Spacer()
            }
        }.onAppear(perform: {
            Lecture.getMyLectureList { (response) in
                do{
                    lectures = try response.get()
                }catch{
                    print(response)
                }
            }
            Lecture.getAllLectureList { (response) in
                do{
                    allLectures = try response.get()
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
func requestLecture(lectureSeq:Int){
    Lecture.requestAddLecture(lectureSeq: lectureSeq) { response in
        
    }
}


struct LectureView_Previews: PreviewProvider {
    static var previews: some View {
        LectureView()
    }
}
