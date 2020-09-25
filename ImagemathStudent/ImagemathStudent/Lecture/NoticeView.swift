//
//  NoticeView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct NoticeView: View {
    
    @State var notices: [Notice] = Notice.getLectureNotice(lectureSeq: 0)
    let lecture:Lecture
    
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text(lecture.name!).font(.system(size: 14))
                Spacer()
            }.padding(.leading)
            Spacer().frame(height: 20)
            Text("공지사항").bold().font(.system(size: 20))
                .padding(.leading)
            Spacer()
            List(self.notices, id: \.noticeSeq){
                notice in NoticeCell(notice: notice)
            }
        }.padding(.top, 20)
        .navigationBarTitle("공지사항")
        .onAppear(perform: {
            Notice.getNoticeList(lectureSeq: lecture.lectureSeq!) { (response) in
                do{
                    notices = try response.get()
                }catch{
                    print(response)
                }
            }
        })
    }
}

struct NoticeCell: View{
    let notice: Notice
    
    var body: some View{
        VStack(alignment: .leading){
            HStack{
                Text("NO. "+String(notice.noticeSeq)).font(.system(size: 12))
                Spacer()
                Text("3시간 전 게시").font(.system(size: 12))
            }
            Spacer().frame(height: 4)
            Text(notice.title)
            Spacer()
            Rectangle().frame(width:30, height: 3)
            Spacer()
            Text(notice.contents)
            Spacer()
        }
        .padding()
        .overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color("borderColor"), lineWidth: 1))
            .frame(height: 200)
    }
    
}

struct NoticeView_Previews: PreviewProvider {
    static var previews: some View {
        NoticeView(lecture: Lecture.getDummyData()[0])
    }
}
