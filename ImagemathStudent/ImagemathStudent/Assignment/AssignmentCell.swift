//
//  AssignmentCell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AssignmentCell: View {
    
    let dailyAssignments: [Assignment]
    
    var body: some View {
        VStack{
            HStack{
                Rectangle().frame(width:2, height: 40)
                    .foregroundColor(Color.init("etoosColor"))
                Text("9. 10 목").font(.system(size: 24))
                Spacer()
            }.padding(.horizontal)
            
            VStack{
                ForEach(dailyAssignments, id: \.assignmentSeq){
                    assignment in
                    
                    ZStack{
                        AssignmentMiniCell(assignment: assignment)
                        NavigationLink(destination: Text("LOL!")){
                            EmptyView()
                        }.buttonStyle(PlainButtonStyle())
                    }
                }
            }.padding()
        }
    }
}
struct AssignmentMiniCell: View{
    let assignment:Assignment
    
    var body: some View{
        VStack(alignment: .trailing){
            HStack{
                Circle().frame(width:12,height: 12)
                Text(assignment.title!)
                Spacer()
            }
            Text(assignment.lectureName!).font(.system(size: 12))
        }
    }
}

struct AssignmentCell_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentCell(dailyAssignments: Assignment.getAssignmentDummyData())
    }
}
