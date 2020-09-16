//
//  ContentView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/05.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct ContentView: View {
        var body: some View {
            MainView()
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

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
