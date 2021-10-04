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
    @State var attachFiles = [ServerFile]()
    
    var body: some View{
        VStack(alignment: .leading){
            HStack{
                Text("NO. "+String(notice.noticeSeq!)).font(.system(size: 12))
                Spacer()
                Text(DateUtils.getRelativeTimeString(unixtime: notice.postTime!)).font(.system(size: 12))
            }
            Spacer().frame(height: 4)
            Text(notice.title!)
            Spacer().frame(height: 24)
            Rectangle().frame(width:30, height: 3)
            Spacer().frame(height: 24)
            Text(notice.contents!)
            Spacer().frame(height: 24)
            ServerFileView(fileList: $attachFiles)
        }
        .padding()
        .overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color("borderColor"), lineWidth: 1))
        .onAppear{
            ServerFile.getNoticeFiles(noticeSeq: notice.noticeSeq!) { (response) in
                do{
                    attachFiles = try response.get()
                }catch{
                    print(response)
                }
            }
        }
    }
    
}

struct NoticeView_Previews: PreviewProvider {
    static var previews: some View {
        NoticeView(lecture: Lecture.getDummyData()[0])
    }
}
