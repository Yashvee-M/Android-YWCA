//
//  UserResults.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/22/24.
//

import Foundation

struct User: Codable{
    var uname : String
    var upassword : String
}

struct UserResults: Codable{
    var name : String
    var date : String
    var fresult : String
    var results : [TestResults]
}

struct TestResults: Codable{
    var result : String
}

struct CurrentUser{
    var name: String = ""
}
var cu = CurrentUser(name:"")

var userResults : [UserResults] = []


