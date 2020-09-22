//
//  SettingView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct SettingView: View {
    @State var userDefaults = UserDefaultsManager()
    @State var logoutAlertVisible = false;
    
    var body: some View {
        VStack(alignment: .leading){
            Text("계정")
                .foregroundColor(Color("etoosColor"))
                .padding(.horizontal)
                .padding(.top,14)
            Button(action: {
                logoutAlertVisible = true;
            }, label: {
                VStack(alignment: .leading){
                    Rectangle()
                        .frame(height: 1)
                        .foregroundColor(Color.gray)
                    Text("로그아웃")
                        .font(.system(size: 12))
                        .foregroundColor(.black)
                        .padding(.horizontal)
                        .padding(.vertical, 12)
                    Rectangle()
                        .frame(height: 1)
                        .foregroundColor(Color.gray)
                }
            })
            Spacer()
        }.navigationBarTitle("환경설정")
        .alert(isPresented: $logoutAlertVisible, content: {
            Alert(title: Text("정말로 로그아웃 하시겠습니까?"),
                  message:Text("로그아웃하면 앱이 종료됩니다."),
                  primaryButton:
                    .destructive(Text("로그아웃"),
                                 action: {
                                    userDefaults.accessToken = ""
                                    exit(0)
                                 }),
                  secondaryButton:
                    .destructive(Text("취소"),
                                 action:{
                                   logoutAlertVisible = false
                                 }))
        })
    }
}

struct SettingView_Previews: PreviewProvider {
    static var previews: some View {
        SettingView()
    }
}
