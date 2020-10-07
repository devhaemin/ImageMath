//
//  APIConnection.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/12.
//  Copyright © 2020 정해민. All rights reserved.
//
import Foundation
import Alamofire

public protocol APIRouteable: URLRequestConvertible{
    
    var baseURL: URL { get }
    var path: String { get }
    var method: HTTPMethod { get }
    var parameters: [String : String]? { get }
    
}

extension APIRouteable {
    var baseURL: URL { return URLs.baseURL! }
    var token:String{
        let userDefaultManager = UserDefaultsManager();
        return userDefaultManager.accessToken;
    }
    func asURLRequest() throws -> URLRequest {
        let url = baseURL.appendingPathComponent(path)
            var request = URLRequest(url: url)
            request.method = method
        
        switch method {
        case .get:
            request = try URLEncodedFormParameterEncoder().encode(parameters, into: request)
        case .post:
            request = try JSONParameterEncoder().encode(parameters, into: request)
        default:
            request = try URLEncodedFormParameterEncoder().encode(parameters, into: request)
        }
        request.setValue(token, forHTTPHeaderField: "x-access-token")
        return request
    }
}
class UserDefaultsManager: ObservableObject {
    @Published var fcmToken: String = UserDefaults.standard.string(forKey: "FcmToken") ?? ""{
        didSet { UserDefaults.standard.set(self.accessToken, forKey: "FcmToken")}
    }
    
    @Published var accessToken: String = UserDefaults.standard.string(forKey: "AccessToken") ?? ""{
        didSet { UserDefaults.standard.set(self.accessToken, forKey: "AccessToken")}
    }
}

struct APIClient{
    public static func perform<T: Decodable>(_ apiRoute: APIRouteable,
                                             completion: @escaping (Result<T, Error>) -> Void) {
        let dataRequest = AF.request(apiRoute)
        dataRequest
            .validate(statusCode: 200..<300)
            .responseDecodable{ (response: AFDataResponse<T>) in
                let responseData = response.data ?? Data()
                let string = String(data: responseData, encoding: .utf8)
                print("Repsonse string: \(string ?? "")")
                switch response.result {
                case .success(let response):
                    completion(.success(response))
                case .failure(let error):
                    completion(.failure(error))
                }
        }
    }
}
