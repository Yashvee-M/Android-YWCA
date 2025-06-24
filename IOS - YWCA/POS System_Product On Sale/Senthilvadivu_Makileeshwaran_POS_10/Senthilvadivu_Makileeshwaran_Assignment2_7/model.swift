//
//  model.swift
//  Senthilvadivu_Makileeshwaran_Assignment2_7
//
//  Created by user266706 on 11/15/24.
//

import Foundation

struct Product{
    var name:String = ""
    var quantity:Int = 0
    var price:Float = 0
    
    
}
struct History{
    var name:String = ""
    var quantity:Int = 0
    var price:Float = 0
    var date:String = ""
}
var product:[Product] = [Product(name:"Computer", quantity:8, price: 400),Product(name:"Monitor", quantity:5, price: 50),Product(name:"Keyboard", quantity:10, price: 60)]
var history:[History] = []


