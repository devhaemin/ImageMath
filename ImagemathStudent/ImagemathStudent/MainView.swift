//
//  MainView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/16.
//  Copyright © 2020 정해민. All rights reserved.
//
//

import SwiftUI

struct MainView: View {
    
    @State var selectedView = 0
    
    
    var body: some View{
        
        TabView(selection: $selectedView){
            
            //*********************************
            //            수강중인 강좌 탭
            //*********************************
            
            NavigationView{
                LectureView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("수강중인 강좌")
            }.tag(0)
            .navigationViewStyle(StackNavigationViewStyle())
            
            
            //*********************************
            //            과제 탭
            //*********************************
            
            NavigationView{
                AssignmentView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("과제")
            }.tag(1)
            .navigationViewStyle(StackNavigationViewStyle())
            
            
            //*********************************
            //            테스트 탭
            //*********************************
            
            
            NavigationView{
                TestView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("테스트")
            }.tag(2)
            .navigationViewStyle(StackNavigationViewStyle())
            
            
            //*********************************
            //            QnA 탭
            //*********************************
            
            
            NavigationView{
                QnAView()
            }.tabItem {
                Image(uiImage: #imageLiteral(resourceName: "second"))
                Text("Q&A")
            }.tag(3)
            .navigationViewStyle(StackNavigationViewStyle())
            
        }.onAppear(){
            self.selectedView = 0
        }
    }
}

struct CheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        return HStack {
            Image(systemName: configuration.isOn ? "checkmark.square" : "square")
                .resizable()
                .frame(width: 22, height: 22)
                .onTapGesture { configuration.isOn.toggle() }
        }
    }
}


struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            MainView()
        }
    }
}
