//
//  LectureCell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/07.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct LectureCell: View{
    let lecture: Lecture
    @State var notices: [Notice]
    var body: some View{
        VStack{
            Spacer().frame( height: 20)
            HStack{
                Text(lecture.name).font(.title).lineLimit(1)
                Spacer()
                VStack(alignment: .trailing, spacing: 0){
                    Text(lecture.time)
                        .font(.system(size: 10))
                    Text(lecture.academyName)
                        .font(.system(size: 10))
                    Text(lecture.totalDate)
                        .font(.system(size: 10))
                }
            }.padding(.horizontal, 14)
            Spacer()
            VStack{
                HStack{
                    Image(uiImage: #imageLiteral(resourceName: "img_notice_loudspeaker"))
                        .resizable()
                        .frame(width: 24, height: 24)
                    
                    Text("공지사항").font(.system(size: 14))
                    Spacer()
                    Text("+ 더보기")
                        .font(.system(size: 12))
                        .foregroundColor(Color("etoosColor"))
                }.padding(.horizontal, 14)
                    .padding(.top, 20)
                Spacer()
                HStack{
                    Rectangle().frame(width: 4, height: 30)
                    VStack{
                        if(notices.count == 0){
                            Text("공지사항이 없습니다.").font(.system(size: 14))
                        }else if(notices.count == 1){
                            Text(notices[0].title).font(.system(size: 14))
                        }else{
                            Text(notices[0].title).font(.system(size: 14))
                            Text(notices[1].title).font(.system(size: 14))
                        }
                    }.onAppear {
                        self.notices = self.lecture.getNoticeInfo()
                    }
                    Spacer()
                }.padding(.bottom, 14)
                    .padding(.horizontal, 14)
                
            }.frame(height: 120)
                .background(Image(uiImage: #imageLiteral(resourceName: "box_lecture_on_notice")).resizable())
        }.overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color("borderColor"), lineWidth: 1))
            .frame(height: 300)
    }
}

struct LectureCell_Previews: PreviewProvider{
    
    static let previewDummyLecture = Lecture.getDummyData()
    static var previews: some View{
        ZStack {
            Color.white
                .edgesIgnoringSafeArea(.all)
            LectureCell(lecture: previewDummyLecture.first!, notices: Notice.getLectureNotice(lectureSeq: 0))
            
        }
    }
}
