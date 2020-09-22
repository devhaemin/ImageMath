//
//  AssignmentDetailView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AssignmentDetailView: View {
    let assignment: Assignment
    let answerFiles = ServerFile.getDummyData()
    let submitFiles = ServerFile.getDummyData()
    
    var body: some View {
        VStack(alignment: .leading){
            /**
             첫번째 블록 ( 제목, 수업 이름, 학원 명)
             */
            VStack(alignment: .leading){
                Text(assignment.lectureName).font(.system(size:10)).foregroundColor(Color.gray)
                Spacer().frame(height: 12)
                Text(assignment.title).foregroundColor(Color.black)
                HStack{
                    Spacer()
                    Text("미제출").font(.system(size:12))
                }
            }.padding(.horizontal)
                .padding(.top,24)
            /**
             날짜 작성 칸
             등록일
             제출 마감일
             수업일
             */
            VStack{
                Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
                HStack{
                    Text("등록일").font(.system(size:12))
                    Spacer()
                    Text("3일 전").font(.system(size:12))
                }.padding(.horizontal)
                    .padding(.vertical,12)
                HStack{
                    Text("제출마감일").font(.system(size:12))
                    Spacer()
                    Text("3일 전").font(.system(size:12))
                }.padding(.horizontal)
                    .padding(.vertical,12)
                HStack{
                    Text("수업일").font(.system(size:12))
                    Spacer()
                    Text("3일 전").font(.system(size:12))
                }.padding(.horizontal)
                    .padding(.vertical,12)
                Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
                    .padding(.bottom,12)
            }.frame(width: 260).padding(.horizontal)
            .padding(.vertical,18)
            /**
             내용 작성 칸
             */
            HStack{
                Rectangle().frame(width: 2, height: 30)
                Text(assignment.contents).font(.system(size: 12))
            }.padding(.horizontal)
            /**
             해설지 파일 설치칸
             */
            VStack(alignment:.leading){
                Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
                Text("해설지").font(.system(size:14)).padding(.leading).padding(.top, 8)
                if(assignment.submitState == 0){
                    ZStack{
                        Rectangle().frame( height: 36).foregroundColor(Color.gray)
                        Text("통과 후 열람가능합니다").foregroundColor(Color.white)
                    }.padding(.bottom, 8)
                }else{
                    //To-do : File List View 만들어서 넣기.
                }
                Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
            }
            VStack(alignment:.leading){
                HStack{
                    Text("제출 내용").font(.system(size:14)).padding(.leading)
                    Button(action: {
                        
                    }) {
                        Text("추가하기").font(.system(size:12)).foregroundColor(Color("etoosColor"))
                    }.padding(.horizontal)
                        .padding(.vertical,12)
                }
            }
            ScrollView (.horizontal, showsIndicators: false) {
                 HStack {
                    ForEach(submitFiles, id: \.fileSeq){ submitfile in
                        Text("")
                    }
                 }
            }.frame(height: 100)
            Spacer()
        }.navigationBarTitle("과제 상세보기")
    }
}

struct AssignmentDetailView_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentDetailView(assignment: Assignment.getAssignmentDummyData()[0])
    }
}
