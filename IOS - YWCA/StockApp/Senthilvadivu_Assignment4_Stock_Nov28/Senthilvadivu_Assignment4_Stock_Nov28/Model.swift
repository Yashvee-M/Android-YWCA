//
//  Model.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/27/24.
//

import Foundation

//Model for get performanceId from "https://ms-finance.p.rapidapi.com/market/v2/auto-complete?q=\(stockName)"
struct ResultValues : Codable{
    var count: Int
    var results : [TStock]
}
struct TStock : Codable {
    var name : String
    var performanceId : String
}

//Model for get the LastPrice from URL
struct ResultPrice : Codable{
    var lastPrice : Float
    var lastClose : Float
    var currencyCode : String
    var currencySymbol : String
}


// Enum to represent Rank with 3 intervals: cold, hot, and veryHot
enum rank: Float {
    case cold = 0.0   // Interval 0 to 0.33
    case hot = 0.5    // Interval 0.34 to 0.67
    case veryHot = 1.0 // Interval 0.68 to 1.0
    
    // Static method to get the description based on the rank value
    static func description(for value: Float) -> String {
        switch value {
        case 0..<0.33:
            return "Cold"
        case 0.34..<0.67:
            return "Hot"
        case 0.68..<1.1:
            return "Very Hot"
        default:
            return "Invalid value"
        }
    }
    
}


