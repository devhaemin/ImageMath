//
//  AssignmentView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AssignmentView: View {
    
    @State var assignments = Assignment.getAssignmentDummyData();
    
    var body: some View {
        VStack{
            HStack{
                Spacer()
                Circle().frame(width:12,height: 12)
                Text("미제출")
                Circle().frame(width:12,height: 12)
                    .foregroundColor(Color.yellow)
                Text("제출")
                Circle().frame(width:12,height: 12)
                    .foregroundColor(Color.red)
                Text("불성실")
                Circle().frame(width:12,height: 12)
                    .foregroundColor(Color("etoosColor"))
                Text("통과")
                
            }.padding(.trailing)
            .padding(.top)
            Spacer().frame(height:14)
            List(self.assignments, id: \.assignmentSeq) { assignment in
                AssignmentCell(dailyAssignments: self.assignments)
            }
        }.navigationBarTitle("", displayMode: .inline)
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
        .onAppear{
            Assignment.getStudentAssignmentList { (response) in
                do{
                    assignments = try response.get()
                }catch{
                    print(response)
                }
            }
        }
    }
}

struct AssignmentView_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentView()
    }
}
