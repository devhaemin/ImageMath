//
//  RegisterView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/30.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct RegisterView: View {
    @State var name = ""
    @State var gender = 0
    @State var birthYear = ""
    @State var birthMonth = ""
    @State var birthDay = ""
    @State var email = ""
    @State var password = ""
    @State var password_re = ""
    @State var phone = ""
    @State var schoolName = ""
    @State var studentCode = ""
    @State var userType = "student"
    @Binding var registerVisible: Bool;

    
    var body: some View {
        ScrollView(.vertical, showsIndicators: true){
            VStack(alignment: .leading){
                VStack(alignment: .leading){
                    Text("이름")
                        .padding()
                    TextField("이름", text: $name)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                
                }
                VStack(alignment: .leading){
                    Text("이메일")
                        .padding()
                    TextField("이메일 입력", text: $email)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                    Text("비밀번호")
                        .padding()
                    TextField("비밀번호 입력", text: $password)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                    Text("비밀번호 확인")
                        .padding()
                    TextField("비밀번호 확인 입력", text: $password_re)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                }
                VStack(alignment: .leading){
                    Text("휴대폰 번호")
                        .padding()
                    TextField("- 등 특수문자를 제외한 숫자만 입력", text: $phone)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                    Text("학교정보")
                        .padding()
                    TextField("00고 또는 N수생 이라고 입력해주세요.", text: $schoolName)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                    Text("학생코드")
                        .padding()
                    TextField("스쿨링 코드를 입력해주세요.", text: $studentCode)
                        .padding()
                        .border(Color("etoosColor"), width: 1)
                }
                Button(action: {
                    let user = User(email: email, password: password, userSeq: nil, name: name, birthday: "0000-00-00", accessToken: nil, fcmToken: nil, gender: 2, userType: "student", phone: phone, studentCode: studentCode, schoolName: schoolName, registerTime: nil)
                    User.register(user: user) { result in
                        do{
                            let user = try result.get()
                            registerVisible = false
                            let userDefaults = UserDefaultsManager()
                            userDefaults.accessToken = user.accessToken ?? ""
                            
                        }catch{
                            print(result)
                        }
                    }
                }, label: {
                    ZStack{
                        Rectangle()
                            .frame(height: 50, alignment: .center)
                            .foregroundColor(Color("etoosColor"))
                        Text("확인")
                            .foregroundColor(.white)
                    }
                })
                .padding(.top)
            }
        }.navigationBarTitle("회원가입")
        .padding()
    }
}

struct RegisterView_Previews: PreviewProvider {
    @State static var registerVisible = true
    static var previews: some View {
        RegisterView(registerVisible: $registerVisible)
    }
}
