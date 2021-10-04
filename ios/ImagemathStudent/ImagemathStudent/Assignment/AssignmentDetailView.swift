//
//  AssignmentDetailView.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/08.
//  Copyright © 2020 정해민. All rights reserved.
//

import SwiftUI
import URLImage
import Alamofire

struct AssignmentDetailView: View {
    let assignment: Assignment
    @State var answerFiles = ServerFile.getDummyData()
    @State var submitFiles = ServerFile.getDummyData()
    @State private var showingImagePicker = false
    @State private var inputImage: UIImage?
     
    func loadImage() {
        guard let inputImage = inputImage else { return }
        submitAssignmentImage(inputImage: inputImage)
    }
    func submitAssignmentImage(inputImage:UIImage){
        let user = UserDefaultsManager()
        AF.upload(multipartFormData: { multipartFormData in
            multipartFormData.append( inputImage.jpegData(compressionQuality: 1)!, withName: "submitFile", fileName: "swift_file.jpeg", mimeType: "image/jpeg")
        }, to: "http://api-doc.imagemath.kr:3000/assignment/submit/"+String(assignment.assignmentSeq)
        , headers: [HTTPHeader(name: "x-access-token", value: user.accessToken)])
        .responseDecodable(of: DefaultServerModel.self) { response in
                debugPrint(response)
            }
    }
    
    var body: some View
    {
        ScrollView(.vertical, showsIndicators: true){
            VStack(alignment: .leading){
                /**
                 첫번째 블록 ( 제목, 수업 이름, 학원 명)
                 */
                VStack(alignment: .leading){
                    Text(assignment.lectureName).font(.system(size:10)).foregroundColor(Color.gray)
                    Spacer().frame(height: 12)
                    Text(assignment.title).foregroundColor(Color.black)
                    HStack{
                        Spacer()
                        Text(assignment.getStatusText()).font(.system(size:12)).foregroundColor(assignment.getStatusColor())
                    }
                }.padding(.horizontal)
                .padding(.top,24)
                /**
                 날짜 작성 칸
                 등록일
                 제출 마감일
                 수업일
                 */
                VStack{
                    Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
                    HStack{
                        Text("등록일").font(.system(size:12))
                        Spacer()
                        Text(DateUtils.getRelativeTimeString(unixtime: assignment.postTime)).font(.system(size:12))
                    }.padding(.horizontal)
                    .padding(.vertical,12)
                    HStack{
                        Text("제출마감일").font(.system(size:12))
                        Spacer()
                        Text(DateUtils.getRelativeTimeString(unixtime: assignment.endTime)).font(.system(size:12))
                    }.padding(.horizontal)
                    .padding(.vertical,12)
                    HStack{
                        Text("수업일").font(.system(size:12))
                        Spacer()
                        Text(DateUtils.getRelativeTimeString(unixtime: assignment.lectureTime)).font(.system(size:12))
                    }.padding(.horizontal)
                    .padding(.vertical,12)
                    Rectangle().frame(height: 1).foregroundColor(Color("borderColor"))
                        .padding(.bottom,12)
                }.frame(width: 260).padding(.horizontal)
                .padding(.vertical,18)
                /**
                 내용 작성 칸
                 */
                HStack{
                    Rectangle().frame(width: 2, height: 30)
                    Text(assignment.contents).font(.system(size: 12))
                }.padding(.horizontal)
                /**
                 해설지 파일 설치칸
                 */
                VStack(alignment:.leading){
                    Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
                    Text("해설지").font(.system(size:14)).padding(.leading).padding(.top, 8)
                    if(assignment.submitState == 0 || assignment.submitState == 1 || assignment.submitState == 5 || assignment.endTime < Int(Date().timeIntervalSince1970) * 1000 ){
                        ZStack{
                            Rectangle().frame( height: 36).foregroundColor(Color.gray)
                            Text("통과 후 열람가능합니다").foregroundColor(Color.white)
                        }.padding(.bottom, 8)
                    }else{
                        ServerFileView(fileList: $answerFiles)
                    }
                    Rectangle().frame(height: 2).foregroundColor(Color("borderColor"))
                }
                VStack(alignment:.leading){
                    HStack{
                        Text("제출 내용").font(.system(size:14)).padding(.leading)
                        Button(action: {
                            showingImagePicker = true
                        }) {
                            Text("추가하기").font(.system(size:12)).foregroundColor(Color("etoosColor"))
                        }.padding(.horizontal)
                        .padding(.vertical,12)
                    }
                }
                ScrollView (.horizontal, showsIndicators: false) {
                    HStack {
                        ForEach(submitFiles, id: \.fileSeq){ submitfile in
                            URLImage(URL.init(string: submitfile.fileUrl!)!){ proxy in
                                proxy.image.resizable()
                                    .aspectRatio(contentMode: .fill)
                                    .clipped()
                            }
                            .frame(width: 100, height: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/, alignment: .center)
                            .padding()
                            
                        }
                    }
                }.frame(height: 100)
                .padding()
            }.navigationBarTitle("과제 상세보기")
            .onAppear{
                ServerFile.getAssignmentAnswerFiles(assignmentSeq: assignment.assignmentSeq) { (response) in
                    do{
                        answerFiles = try response.get()
                    }catch{
                        print(response)
                    }
                }
                ServerFile.getAssignmentSubmitFiles(assignmentSeq: assignment.assignmentSeq) { (response) in
                    do{
                        submitFiles = try response.get()
                    }catch{
                        print(response)
                    }
                }
            }
            .sheet(isPresented: $showingImagePicker, onDismiss: loadImage) {
                ImagePicker(image: self.$inputImage)
            }
        }
    }
}

struct AssignmentDetailView_Previews: PreviewProvider {
    static var previews: some View {
        AssignmentDetailView(assignment: Assignment.getAssignmentDummyData()[0])
    }
}


extension Assignment{
    func getStatusColor() -> Color{
        switch submitState{
        case 0:
            if (endTime < Int(Date().timeIntervalSince1970) * 1000){
                return .red
            }else{
                return .black
            }
        case 1:
            return .yellow
        case 2:
            return Color("etoosColor")
        case 3:
            return Color("etoosColor")
        case 4:
            return Color("etoosColor")
        case 5:
            return .red
        default:
            return .black
        }
    }
    func getStatusText() -> String{
        switch submitState{
        case 0:
            if (endTime < Int(Date().timeIntervalSince1970) * 1000 ){
                return "불성실"
            }else{
                return "미제출"
            }
        case 1:
            return "제출"
        case 2:
            return "A등급"
        case 3:
            return "B등급"
        case 4:
            return "C등급"
        case 5:
            return "불성실"
        default:
            return "미제출"
        }
    }
}

