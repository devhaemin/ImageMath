//
//  AssignmentView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AssignmentView: View {
    
    @State var assignments = Assignment.getAssignmentDummyData()
    @State var dateHolders:[AssignmentDateHolder] = AssignmentDateHolder.getDummyData()
    
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
            List {
                ForEach(dateHolders.sorted{
                    $0.date > $1.date
                }, id: \.date){ dateHolder in
                    Section(header: HStack{
                        Rectangle().frame(width:2, height: 40)
                            .foregroundColor(Color.init("etoosColor"))
                        Text(getTimeDate(date: dateHolder.date)).font(.system(size: 24))
                        Spacer()
                    }
                    .padding(.horizontal)){
                        ForEach(dateHolder.items, id: \.assignmentSeq){ assignment in
                            AssignmentMiniCell(assignment: assignment).listRowInsets(EdgeInsets())
                        }
                    }.listRowInsets(EdgeInsets())
                    .padding(.vertical,8)
                }.background(Color.white)
                .listStyle(GroupedListStyle())
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
                    let resAssignments = try response.get()
                    dateHolders.removeAll()
                    for assignment in resAssignments{
                        var hasSame = false;
                        for dateHolder in dateHolders{
                            if(assignment.endTime / (1000*3600*24) == dateHolder.date){
                                hasSame = true;
                            }
                        }
                        if(!hasSame){
                            dateHolders.append(AssignmentDateHolder(date: assignment.endTime / (1000*3600*24)))
                        }
                    }
                    for i in 0..<dateHolders.count{
                        var arr = [Assignment]()
                        for assignment in resAssignments{
                            if((assignment.endTime / (1000*3600*24)) == (dateHolders[i].date) ){
                                arr.append(assignment)
                            }
                        }
                        dateHolders[i].items = arr;
                    }
                }catch{
                    print(response)
                }
            }
        }
    }
}
func getTimeDate(date : Int) -> String {
    let date = Date(timeIntervalSince1970: TimeInterval(date*(3600*24)))
    let dateFormatter = DateFormatter()
    dateFormatter.timeZone = TimeZone(abbreviation: "GMT+9") //Set timezone that you want
    dateFormatter.locale = Locale(identifier: "ko-KR")
    dateFormatter.dateFormat = "MM. dd E"
    let strDate = dateFormatter.string(from: date)
    return strDate
}

struct AssignmentDateHolder{
    var date:Int
    var items: [Assignment] = []
}
extension AssignmentDateHolder{
    static func getDummyData() -> [AssignmentDateHolder] {
        return [AssignmentDateHolder(date: 18522, items: Assignment.getAssignmentDummyData())]
    }
}

struct AssignmentView_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentView()
    }
}
