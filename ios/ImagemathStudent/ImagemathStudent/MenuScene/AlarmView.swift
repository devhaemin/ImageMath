//
//  AlarmView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct AlarmView: View {
    @State var alarmList:[Alarm] = Alarm.getDummyData()
    
    var body: some View {
        List{
        ForEach(alarmList, id: \.alarmSeq){ alarm in
            AlarmCell(alarm: alarm)
        }
    }.onAppear{
        Alarm.getAlarmList { (response) in
            do{
                alarmList = try response.get()
            }catch{
                print(response)
            }
        }
    }.navigationBarTitle("알림 목록")
    }
}
struct AlarmCell: View{
    
    let alarm:Alarm
    
    var body:some View{
        VStack(alignment: .leading){
            Text(alarm.title!)
            HStack{
                Image(uiImage: #imageLiteral(resourceName: "img_notice_loudspeaker")).resizable()
                    .frame(width: 30, height: 24, alignment: .center)
                    .aspectRatio(contentMode: .fill)
                Text(alarm.content!)
            }
            HStack{
                Spacer()
            Text(DateUtils.getRelativeTimeString(unixtime: alarm.postTime!))
            }
            Rectangle().frame( height: 1, alignment: .center)
                .foregroundColor(Color.gray.opacity(0.6))
        }.padding(.vertical, 7)
    }
}

struct AlarmView_Previews: PreviewProvider {
    static var previews: some View {
        AlarmView()
    }
}
