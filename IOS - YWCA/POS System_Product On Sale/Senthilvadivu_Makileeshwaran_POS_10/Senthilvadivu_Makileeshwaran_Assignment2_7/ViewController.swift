//
//  ViewController.swift
//  Senthilvadivu_Makileeshwaran_Assignment2_7
//
//  Created by user266706 on 11/15/24.
//

import UIKit

//Initial view Controller
class ViewController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {
    
    //Array of product object for the Product class with Value
    //var product:[Product] = [Product(name:"Computer", quantity:8, price: 400),Product(name:"Monitor", quantity:5, price: 50),Product(name:"Keyboard", quantity:10, price: 60)]
    //var history:[History] = []
    var selectedProduct:String = ""
    var selectedPrice: Float = 0
    var selectedQuantity: Int = 0
    var availableQuantity: Int = 0
    var selectedPickerRow: Int = 0
    var displayTotal:Float = 0
    
    //Outlet for display total Label
    @IBOutlet weak var totalPrice: UILabel!
    
    //Outlet for Product PickerView
    @IBOutlet weak var productPicker: UIPickerView!
    
    //Outlet for Selected Product Label
    @IBOutlet weak var displayProductSelected: UILabel!
    
    //Outlet for display selected Quantity
    @IBOutlet weak var displayQuantity: UILabel!
    
    //flag set true in the begining for new number
    var isNewNumber = true
    
    //Action to add displayQuantity label by each number button pressed
    @IBAction func digitPressed(_ sender: UIButton) {
        
        //get titleLabel from pressedButton and add it to displayQuantity and calculate TotalPrice
        if let textOfBtn = sender.titleLabel?.text, let displayText = displayQuantity.text {
            if isNewNumber {
                displayQuantity.text = sender.titleLabel?.text
                if let text = displayQuantity.text, let intVaue = Int(text){   //Int value of displayQuantity
                    selectedQuantity = intVaue
                }
                calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
                isNewNumber = false
                
            }else{
                displayQuantity.text = "\(displayText)\(textOfBtn)"
                if let text = displayQuantity.text, let intVaue = Int(text){
                    selectedQuantity = intVaue
                }
                calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
            }
            
        }
    }
    
    //Action to clear displayQuantity Label to 0 and calculate totalPrice
    @IBAction func clearPressed() {
        displayQuantity.text = "0"
        if let text = displayQuantity.text, let intVaue = Int(text){
            selectedQuantity = intVaue
        }
        calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
        isNewNumber = true
    }
    
    //Action to remove last place from displayQuantity Label and update calculate TotalPrice
    @IBAction func backPressed() {
        
        displayQuantity.text?.popLast()
        if displayQuantity.text == ""{
            displayQuantity.text = "0"
            if let text = displayQuantity.text, let intVaue = Int(text){
                selectedQuantity = intVaue
            }
            calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
            isNewNumber = true
        }
        if let text = displayQuantity.text, let intVaue = Int(text){
            selectedQuantity = intVaue
        }
        calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
    }
    
    
    //Action to decrease productQuantity by comparing it with available quantity and reload pickerView, Update History for each buy
    @IBAction func buyPressed(_ sender: UIButton) {
        
        //check the productQuantity available in the inventry
        if selectedQuantity < product[selectedPickerRow].quantity{
            product[selectedPickerRow].quantity -= selectedQuantity
            //get the CurrentTime
            let currentDateTime = Date()
            let formatter = DateFormatter()
            formatter.dateStyle = .medium
            formatter.timeStyle = .medium
            let dateTimeString = formatter.string(from: currentDateTime)
            let purchaseTime = " \(dateTimeString)"
            let newhistory = History(name:selectedProduct,quantity: selectedQuantity,price:displayTotal,date:purchaseTime)
            history.append(newhistory)
            
            productPicker.reloadAllComponents()
            
            
            //Alert user for sucessful purchase
            let alert = UIAlertController(title: "Success", message: "Buy Success", preferredStyle: .alert)
            
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            
            present(alert, animated: true, completion: nil)
        }
        else{
            //if product quantity exceed alert user with message
            let alert = UIAlertController(title: "Exceed", message: "Product Quantity exceed available", preferredStyle: .alert)
            
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            
            present(alert, animated: true, completion: nil)
            
        }
        displayQuantity.text = "0"
        isNewNumber = true//set isNumber flag to true for new entry
        if let text = displayQuantity.text, let intVaue = Int(text){
            selectedQuantity = intVaue
        }
        calculateTotal(pquantity: selectedQuantity, pprice: selectedPrice)
    }
    
    //Ask code from user if successful entry perform Segue to tabBar
    @IBAction func managerPressed(_ sender: UIButton) {
        self.showAlertForInput()
    }

    //return number of Components to pickerView
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    //return number of rows to pickerView
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return product.count
    }
    //set row title to the each each row of pickerView
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return "\(product[row].name)\t \(product[row].quantity)\t  \(product[row].price)"
        
    }
    //get selected row number from pickerView and assign selected values to variables
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        selectedPickerRow = row
        selectedProduct = "\(product[row].name)"
        selectedPrice = Float(product[row].price)
        availableQuantity = Int(product[row].quantity)
        displayProductSelected.text = selectedProduct
    }
    
    //Load first when ViewController start
    override func viewDidLoad() {
        super.viewDidLoad()
        productPicker.dataSource = self
        productPicker.delegate = self
        productPicker.reloadAllComponents()//reload pickerView when view controller start
        
    }
    
    //Calculate total in each value change in displayQuantity Label
    func calculateTotal(pquantity:Int,pprice:Float){
        displayTotal = Float(pquantity) * pprice
        if pquantity > availableQuantity{
            totalPrice.text = "\(displayTotal)"
            totalPrice.textColor = UIColor.red
        }
        else{
            totalPrice.text = "\(displayTotal)"
            totalPrice.textColor = UIColor.black//set color for label when quantity exceed available
            
        }
        
    }
    
    //display alert box when managerButton pressed
    func showAlertForInput() {
           let alert = UIAlertController(title: "Enter Code", message: "Please enter the code", preferredStyle: .alert)
           
           // Add a text field to the alert
           alert.addTextField { (textField) in
               textField.placeholder = "Enter code"
               textField.isSecureTextEntry = true //  to hide the input
           }
           
           // Add an action for when the user taps the "OK" button
           let okAction = UIAlertAction(title: "OK", style: .default) { [weak self] _ in
               if let textField = alert.textFields?.first, let inputText = textField.text {
                   // Check if the entered text is "1234"
                   if inputText == "1234" {
                       // Perform the segue if the input matches
                       self?.performSegue(withIdentifier: "VCtoEditVCsegue", sender: self)
                   } else {
                       // Optionally show an error message
                       let errorAlert = UIAlertController(title: "Invalid Code", message: "The code you entered is incorrect.", preferredStyle: .alert)
                       errorAlert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                       self?.present(errorAlert, animated: true, completion: nil)
                   }
               }
           }
           
           // Add a "Cancel" action
           let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
           
           // Add the actions to the alert
           alert.addAction(okAction)
           alert.addAction(cancelAction)
           
           // Present the alert
           self.present(alert, animated: true, completion: nil)
       }


}

