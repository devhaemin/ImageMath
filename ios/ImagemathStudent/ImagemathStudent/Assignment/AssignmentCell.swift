//
//  AssignmentCell.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AssignmentMiniCell: View{
    let assignment:Assignment
    
    var body: some View{
            VStack(alignment: .trailing){
                HStack{
                    Circle().frame(width:12,height: 12).foregroundColor(assignment.getStatusColor())
                    Text(assignment.title)
                    Spacer()
                }
                Text(assignment.lectureName).font(.system(size: 12))
            }
    }
}

struct AssignmentMiniCell_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentMiniCell(assignment: Assignment.getAssignmentDummyData()[0])
    }
}
