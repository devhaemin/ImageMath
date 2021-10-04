//
//  QnADetailView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/16.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct QnADetailView: View {
    let question:Question;
    @State var answers = [Answer]()
    
    var body: some View {
        VStack{
            QnACell(question: question)
            Rectangle().frame( height: 1, alignment: .center)
            Text("답변")
            ScrollView(.vertical, showsIndicators: true){
                VStack{
                    ForEach(answers, id: \.answerSeq) { answer in
                        AnswerCell(answer: answer)
                    }
                }
            }
        }.navigationBarTitle("답변 확인하기")
        .onAppear{
            Answer.getAnswerList(questionSeq: question.questionSeq) { (response) in
                do{
                    answers = try response.get();
                }catch{
                    print(response);
                }
            }
        }
    }
}

struct QnADetailView_Previews: PreviewProvider {
    static var previews: some View {
        QnADetailView(question: Question.getDummyData()[0])
    }
}
