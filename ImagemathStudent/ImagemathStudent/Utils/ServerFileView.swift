//
//  ServerFileView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/27.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI

struct ServerFileView: View {
    @Binding var fileList:[ServerFile]
    
    var body: some View {
        VStack{
            ForEach(fileList, id: \.fileSeq){ file in
                HStack{
                    Image(uiImage: #imageLiteral(resourceName: "img_file_etoos"))
                    Spacer().frame(width:8)
                    Text(file.fileName!).font(.system(size: 12))
                    Spacer()
                }
                .padding(.horizontal)
                .padding(.vertical,4)
                .background(Color.gray.opacity(0.4))
                .listRowInsets(EdgeInsets())
            }
        }
    }
}

struct ServerFileView_Previews: PreviewProvider {
    @State static var fileList = ServerFile.getDummyData()
    static var previews: some View {
        ServerFileView(fileList: $fileList)
    }
}
