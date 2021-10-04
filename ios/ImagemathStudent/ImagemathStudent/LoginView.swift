//
//  LoginView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/22.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    
    @State var email:String = ""
    @State var password:String = ""
    @State var isOn:Bool = false
    
    @State var userDefaults = UserDefaultsManager()
    
    @Binding var loginAble: Bool
    @Binding var isNetworkAlertVisible: Bool
    @Binding var registerVisible:Bool
    
    var body: some View {
        VStack{
            Spacer().frame(height:30)
            Image(uiImage: #imageLiteral(resourceName: "logo_img_small"))
                .resizable()
                .frame(width: 180,height: 60, alignment: .center)
                .aspectRatio(contentMode: .fit)
            Spacer()
            VStack{
                
                ZStack{
                    Rectangle()
                        .stroke()
                        .foregroundColor(Color("etoosColor"))
                        .frame(height:40)
                    TextField("이메일", text: $email)
                        .padding(10)
                }.padding(.horizontal)
                
                ZStack{
                    Rectangle()
                        .stroke()
                        .foregroundColor(Color("etoosColor"))
                        .frame(height:40)
                    SecureField("비밀번호", text: $password)
                        .padding(10)
                }.padding(.horizontal)
                
                
                HStack{
                    Toggle(isOn:$isOn){
                        Text("")
                    }
                    .toggleStyle(CheckboxToggleStyle())
                    .foregroundColor(.gray)
                    Text("이메일 저장하기")
                        .foregroundColor(.gray)
                        .font(.system(size: 12))
                    Spacer()
                }
                .padding(.leading,18)
                .padding(.vertical,12)
                
                Button(action: {
                    User.emailLogin(email: email,password: password){ response in
                        do{
                            let user:User
                            user = try response.get()
                            loginAble = true;
                            userDefaults.accessToken = user.accessToken!;
                        }catch{
                            isNetworkAlertVisible = true
                            print("Failed!")
                        }
                    }
                }, label: {
                    ZStack{
                        RoundedRectangle(cornerRadius: 24)
                            .frame(height:30)
                            .foregroundColor(Color("etoosColor"))
                            .padding(.horizontal)
                        Text("로그인")
                            .font(.system(size:12))
                            .foregroundColor(.white)
                    }
                })
                Rectangle()
                    .frame(height:1)
                    .padding(.horizontal)
                HStack{
                    Button("회원가입") {
                        registerVisible = true
                    }.foregroundColor(.black).font(.system(size:12))
                    Text(".")
                        .padding(.bottom,10)
                    Button("비밀번호 찾기") {
                        
                    }.foregroundColor(.black).font(.system(size:12))
                }
            }
            Spacer()
            Image(uiImage: #imageLiteral(resourceName: "logo_etoos"))
                .resizable()
                .frame(width: 80, height: 20, alignment: .center)
            Spacer().frame(height:20)
        }.padding()
    }
}
struct LoginView_Previews: PreviewProvider {
    @State static var loginAble : Bool = false;
    @State static var isNetworkAlertVisible = false;
    @State static var registerVisible = false;
    static var previews: some View {
        LoginView(loginAble: $loginAble,isNetworkAlertVisible:$isNetworkAlertVisible, registerVisible: $registerVisible )
    }
}
