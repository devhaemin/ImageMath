//
//  ContentView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/05.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    @ObservedObject var userDefaultsManager = UserDefaultsManager()
    @State var loginAble:Bool = false;
    @State var isNetworkAlert:Bool = false;
    @State var autoLoginAble:Bool = true;
    @State var registerVisible:Bool = false;
    
        var body: some View {
            if(autoLoginAble){
                Text("로그인 중입니다...")
                    .onAppear(perform: {
                        User.tokenLogin { (response) in
                            do{
                                _ = try response.get()
                                loginAble = true;
                                autoLoginAble = false;
                            }catch{
                                loginAble = false;
                                autoLoginAble = false;
                            }
                        }
                        User.sendPushToken(fcmToken: userDefaultsManager.fcmToken){ (response) in
                            
                        }
                    })
            }else if(loginAble){
                MainView().background(Color.white)
                    .onAppear{
                        User.sendPushToken(fcmToken: userDefaultsManager.fcmToken){ (response) in
                            
                        }
                    }
            }else{
                ZStack{
                    if(!registerVisible){
                LoginView(loginAble: $loginAble, isNetworkAlertVisible: $isNetworkAlert, registerVisible: $registerVisible)
                    .alert(isPresented: $isNetworkAlert, content: {
                    Alert(title: Text("네트워크 오류"),message: Text("아이디 혹은 비밀번호가 일치하지 않습니다.\n혹은 네트워크 연결 상태를 확인해주세요."))
                    })
                    }else{
                        RegisterView()
                    }
                }
            }
        }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
