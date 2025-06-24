//
//  EditViewController.swift
//  Senthilvadivu_Makileeshwaran_Assignment2_7
//
//  Created by user266706 on 11/18/24.
//

import Foundation
import UIKit

//class for edit product details
class EditViewController:UIViewController,UITableViewDataSource,UITableViewDelegate{
    var selectedrow:Int = -1 //to know nothing selected from tableView for new product entry
    //Outlet for tableView
    @IBOutlet weak var displayTableView: UITableView!
    
    //Outlet for View to addProduct when plus pressed
    @IBOutlet weak var addView: UIView!
    
    //Action to plusTapped
    @IBAction func plusTapped() {
        addView.isHidden = false
        
        //if row selected update textfields with product previous values
        if selectedrow != -1{
            titletext.text = product[selectedrow].name
            valuetext.text = "\(product[selectedrow].price)"
            quantitytext.text = "\(product[selectedrow].quantity)"
        }
    }
    
    //Action to save product details entered
    @IBAction func savePressed() {
        if selectedrow != -1{
           //keep previous name and update price and quantity from labels
            let addname = product[selectedrow].name
                if let quantity = quantitytext.text, let intValue = Int(quantity){
                    let addquantity = intValue
                    
                    if let price = valuetext.text, let floatValue = Float(price){
                        let addprice = floatValue
                        
                        let newproduct = Product(name:addname,quantity:addquantity,price:addprice)
                        product[selectedrow]=newproduct
                        quantitytext.text = ""
                        titletext.text = ""
                        valuetext.text = ""
                    }}
            selectedrow = -1
        }
        
        //add new product entry to Product structure
        if let name = titletext.text{
            let addname = name
            
            if let quantity = quantitytext.text, let intValue = Int(quantity){
                let addquantity = intValue
                
                if let price = valuetext.text, let floatValue = Float(price){
                    let addprice = floatValue
                    
                    let newproduct = Product(name:addname,quantity:addquantity,price:addprice)
                    product.append(newproduct)
                    quantitytext.text = ""
                    titletext.text = ""
                    valuetext.text = ""
                }}}
        else{
            //display alert incase of wrong entry
            let alert = UIAlertController(title: "Incomplete Entry", message: "Update Correct Entry", preferredStyle: .alert)
            
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            
            present(alert, animated: true, completion: nil)
        }
    }
    
   
    //Cancel option to show tableView again by hidding addView
    @IBAction func cancelPressed() {
        addView.isHidden = true
        displayTableView.reloadData()
    }
    
    //dismiss current view controller
    @IBAction func DonePressed() {
        //show segue to viewController from EditVC for loading current product pickerView
        self.performSegue(withIdentifier: "editVCtoVCsegue", sender: self)
    }
    
     
    //title Label Outlet
    @IBOutlet weak var titletext: UITextField!
    
    //price Label Outlet
    @IBOutlet weak var valuetext: UITextField!
    
    //quantity Label Outlet
    @IBOutlet weak var quantitytext: UITextField!
  
    
   //Load first when EditViewController Starts
    override func viewDidLoad() {
        super.viewDidLoad()
        displayTableView.dataSource = self
        displayTableView.delegate = self
        addView.isHidden = true
    }
    //Add sections to TableView
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    //set number of rows to tableView
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return product.count
    }
    //Assign value for rows in TableView
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "mycellid", for: indexPath) //Reusable of cell prototype with mycellid identifier
        print(indexPath.row)
        
        cell.textLabel?.text = "\(product[indexPath.row].name)\t(\(product[indexPath.row].quantity))"  //set title for cell
        
        cell.detailTextLabel?.text = "\(product[indexPath.row].price)"//set subtitle for cell
        return cell
    }
    
    //get selected row from tableView
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
            
            selectedrow = indexPath.row
            
            // Deselect the row
            tableView.deselectRow(at: indexPath, animated: true)
    }
    
    
    
}
