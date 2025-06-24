//
//  Question.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/21/24.
//

import Foundation
struct Question{
    var text : String
    var type : ResponseType
    var answers : [Answer]
}

enum ResponseType{
    case single,multiple,ranged
}

struct Answer {
    var text : String
    var type : AnimalType
}

enum AnimalType : Character{
    case lion = "🦁", cat = "🐱", rabbit = "🐰", turtle = "🐢"
    var definition : String {
        switch self{
        case .lion :
            return "You are incredibily outgoing. You surround yourself with people you love and enjoy activities with your friends."
        case .cat:
            return "Mischievous, yet mild-tempered, you enjoy doing things on your own terms."
        case .rabbit:
            return "You love everything that's soft. You are healthy and full of energy."
        case .turtle:
            return "You are wise beyond your years, and you focus on the details. Sloz and steady wins the race."
        }
    }
}

var questions: [Question] = []


