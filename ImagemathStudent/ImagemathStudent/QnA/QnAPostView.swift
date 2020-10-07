//
//  QnAPostView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/29.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI
import Alamofire

struct QnAPostView: View {
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var title = ""
    @State var contents = ""
    @State private var showingImagePicker = false
    @State private var inputImage: UIImage? = nil
    @State private var questionSeq: Int? = nil
    @State private var inputImages: [UIImage] = [UIImage]()
    @State private var inputFiles: [ServerFile] = [ServerFile]()
    
    @State private var fileSeq = 0;
     
    func loadImage() {
        guard let inputImage = inputImage else { return }
        inputImages.append(inputImage)
        inputFiles.append(ServerFile(fileSeq: fileSeq, fileName: String("fileSeq"), fileType: nil, postSeq: nil, uploadTime: nil, userSeq: nil, fileUrl: nil))
        fileSeq+=1
    }
    func submitAssignmentImage(inputImage:UIImage){
        let user = UserDefaultsManager()
        AF.upload(multipartFormData: { multipartFormData in
            multipartFormData.append( inputImage.jpegData(compressionQuality: 1)!, withName: "file", fileName: "swift_file.jpeg", mimeType: "image/jpeg")
        }, to: "http://api-doc.imagemath.kr:3000/qna/editQuestion/"+String(questionSeq!)
        , headers: [HTTPHeader(name: "x-access-token", value: user.accessToken)])
        .responseDecodable(of: Question.self) { response in
            self.presentationMode.wrappedValue.dismiss()
        }
    }
    func postQuestion(){
        Question.postQuestion(title: title, contents: contents) { (response) in
            do{
                let question = try response.get()
                questionSeq = question.questionSeq
                for image in inputImages{
                    submitAssignmentImage(inputImage: image)
                }
            }catch{
                
            }
        }
    }
    
    var body: some View {
        VStack(alignment: .leading){
            TextField("제목", text: $title).font(.system(size: 30))
                .padding()
                .padding(.top, 30)
            Rectangle().frame(width: 30, height: 3, alignment: .leading)
                .padding()
            TextField("내용을 입력해주세요.", text: $contents).font(.system(size: 16))
                .padding()
                .frame(height: 200, alignment: .top)
            ServerFileView(fileList: $inputFiles)
            Spacer()
            Button(action: {
                postQuestion()
            }, label: {
                ZStack{
                    Rectangle().frame(height: 60, alignment: .center)
                        .foregroundColor(Color("etoosColor"))
                    Text("올리기").foregroundColor(.white)
                }
            })
        }
        .navigationBarTitle("질문 올리기")
        .navigationBarItems(trailing: Button(action: {
            showingImagePicker = true
        }, label: {
            Image(uiImage: #imageLiteral(resourceName: "img_file_black")).resizable().frame(width: 30, height: 30).aspectRatio(contentMode: .fill)
        }))
        .sheet(isPresented: $showingImagePicker, onDismiss: loadImage) {
            ImagePicker(image: self.$inputImage)
        }
    }
}

struct QnAPostView_Previews: PreviewProvider {
    static var previews: some View {
        QnAPostView()
    }
}
