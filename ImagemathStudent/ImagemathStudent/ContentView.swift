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
                    })
            }else if(loginAble){
                MainView()
            }else{
                LoginView(loginAble: $loginAble, isNetworkAlertVisible: $isNetworkAlert)
                    .alert(isPresented: $isNetworkAlert, content: {
                    Alert(title: Text("네트워크 오류"),message: Text("아이디 혹은 비밀번호가 일치하지 않습니다.\n혹은 네트워크 연결 상태를 확인해주세요."))
                    })
            }
        }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
