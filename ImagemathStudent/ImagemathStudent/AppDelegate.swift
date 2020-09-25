//
//  AppDelegate.swift
//  ImagemathStudent
//
//  Created by 정해민 on 2020/09/05.
//  Copyright © 2020 정해민. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        UITableView.appearance().backgroundColor = UIColor.white
        if #available(iOS 14.0, *) {
                // iOS 14 doesn't have extra separators below the list by default.
            } else {
                // To remove only extra separators below the list:
                UITableView.appearance().tableFooterView = UIView()
            }

            // To remove all separators including the actual ones:
        UITableView.appearance().separatorColor = .clear
        UINavigationBar.appearance().backgroundColor = .white
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

