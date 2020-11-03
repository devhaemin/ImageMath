//
//  QnACell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/09.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct QnACell: View {
    
    let question:Question
    @State var attachFiles = ServerFile.getDummyData()
    
    var body: some View {
        ZStack{
           
            HStack(alignment:.top){
                VStack(alignment: .leading){
                    Text(question.title)
                    Rectangle().frame(width:50,height: 3).foregroundColor(getColor())
                    Spacer()
                    Text(question.contents)
                    ServerFileView(fileList: $attachFiles)
                }
                Spacer()
                Text(getAnswerPostTime()).font(.system(size:12)).foregroundColor(Color.gray)
            }.padding()
            .frame(height:210)
            .overlay(RoundedRectangle(cornerRadius: 4).stroke().foregroundColor(Color("borderColor")))
        }.onAppear(perform: {
            ServerFile.getQuestionAttachFile(questionSeq: question.questionSeq) { (response) in
                do{
                    attachFiles = try response.get()
                }catch{
                    print(response)
                }
            }
        })
        
    }
    func getAnswerPostTime()->String{
        if( question.updateTime == 0){
            return "아직 답변 대기중인 질문입니다."
        }else{
            return DateUtils.getRelativeTimeString(unixtime: question.updateTime)
        }
    }
    func getColor()->Color{
        if( question.updateTime == 0){
            return Color.red
        }else{
            return Color("etoosColor");
        }
    }
}
struct AnswerCell: View {
    
    let answer:Answer
    @State var attachFiles = ServerFile.getDummyData()
    
    var body: some View {
        ZStack{
            
            HStack(alignment:.top){
                VStack(alignment: .leading){
                    Text(answer.title)
                    Rectangle().frame(width:50,height: 3).foregroundColor(Color.red)
                    Spacer()
                    Text(answer.contents)
                    ServerFileView(fileList: $attachFiles)
                }
                Spacer()
                Text(getAnswerPostTime()).font(.system(size:12)).foregroundColor(Color.gray)
            }.padding()
        }.frame(height:210)
        .overlay(RoundedRectangle(cornerRadius: 4).stroke().foregroundColor(Color("borderColor")))
        .onAppear(perform: {
            ServerFile.getAnswerAttachFile(answerSeq: answer.answerSeq) { (response) in
                do{
                    attachFiles = try response.get()
                }catch{
                    print(response)
                }
            }
        })
        
        
    }
    func getAnswerPostTime()->String{
        DateUtils.getRelativeTimeString(unixtime: answer.updateTime)
        
    }
}

struct QnACell_Previews: PreviewProvider {
    static var previews: some View {
        QnACell(question: Question.getDummyData()[0])
    }
}
