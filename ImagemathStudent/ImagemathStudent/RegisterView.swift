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
    @State var gender = ""
    @State var birthday = ""
    @State var email = ""
    @State var password = ""
    @State var password_re = ""
    @State var phone = ""
    @State var schoolName = ""
    @State var studentCode = ""
    let userType = "student"
    
    var body: some View {
        VStack{
            TextField("이름", text: $name)
            TextField("성별", text: $gender)
            TextField("생년월일", text: $birthday)
            TextField("이메일", text: $email)
            TextField("비밀번호", text: $password)
            TextField("비밀번호 확인", text: $password_re)
            TextField("휴대폰 번호", text: $phone)
            TextField("학교 정보", text: $schoolName)
            TextField("학생 코드", text: $studentCode)
        }
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}
