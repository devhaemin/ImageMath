//
//  TestDetailView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct TestDetailView: View {
    let test:Test
    @State var answerFiles = ServerFile.getDummyData()
    
    var body: some View {
        VStack(alignment: .leading){
            /**
             첫번째 블록 ( 제목, 수업 이름, 학원 명)
             */
            VStack(alignment: .leading){
                Text(test.lectureName).font(.system(size:10)).foregroundColor(Color.gray)
                Spacer().frame(height: 12)
                Text(test.title).foregroundColor(Color.black)
            }.padding(.horizontal)
                .padding(.top,24)
            /**
             날짜 작성 칸
             등록일
             제출 마감일
             수업일
             */
            HStack{
            VStack{
                Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
                HStack{
                    Text("등록일").font(.system(size:12))
                    Spacer()
                    Text(DateUtils.getRelativeTimeString(unixtime: test.postTime)).font(.system(size:12))
                }.padding(.horizontal)
                    .padding(.vertical,12)
                HStack{
                    Text("수업일").font(.system(size:12))
                    Spacer()
                    Text(DateUtils.getRelativeTimeString(unixtime: test.lectureTime)).font(.system(size:12))
                }.padding(.horizontal)
                    .padding(.vertical,12)
                Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
            }.padding(.horizontal)
                .padding(.vertical,18)
                ZStack{
                    Image(uiImage: #imageLiteral(resourceName: "box_showscore_on")).resizable()
                    VStack{
                        Spacer()
                        Text(String(test.score)+"점").foregroundColor(Color("etoosColor"))
                        Spacer()
                        Text(String(test.rank)+"등").foregroundColor(Color.white)
                        Spacer()
                    }
                }.frame(width: 70, height: 80)
                    .padding(.trailing,32)
            }
            /**
             내용 작성 칸
             */
            HStack{
                Rectangle().frame(width: 2, height: 30)
                Text(test.contents).font(.system(size: 12))
            }.padding(.horizontal)
            /**
             해설지 파일 설치칸
             */
            VStack(alignment:.leading){
                Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
                Text("해설지").font(.system(size:14)).padding(.leading).padding(.top, 8)
                Spacer().frame(height: 30)
                ServerFileView(fileList: $answerFiles)
                Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
            }.padding(.vertical)
            Spacer()
        }.navigationBarTitle("테스트 상세보기")
        .onAppear(perform: {
            ServerFile.getTestAnswerFiles(testSeq: test.testSeq) { (response) in
                do{
                    answerFiles = try response.get();
                    print(answerFiles)
                }catch{
                    print(response)
                }
            }
        })
    }
}

struct TestDetailView_Previews: PreviewProvider {
    static var previews: some View {
        TestDetailView(test: Test.getDummyData()[0])
    }
}
