//
//  StockDetailViewController.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 12/2/24.
//

import UIKit
import CoreData


//For current Stock item details from HomeTableViewController
class StockDetailViewController: UIViewController {
    
    //gets value from HomeViewController using prepare Segue
    var selectedStock: Stock?
        
        // Outlets for displaying stock details - name,lastPrice, status, rank:
        @IBOutlet weak var stockNameLabel: UILabel!
    
        @IBOutlet weak var stockPriceLabel: UILabel!
        
        @IBOutlet weak var statusSegmentControl: UISegmentedControl!
    
        @IBOutlet weak var rankSelectedSlider: UISlider!
    
        //Outlet for saveButton to make it hidden while start and when edit delected to make it available to save
        @IBOutlet weak var saveButton: UIButton!
       
    
        //name of stock from selected stock object
        var stockName = ""
        //to get selected status from status segment control
        var selectedStatus:Int16 = 0
        //to get selected index from slider
        var selectedRank:Float = 0
    
        override func viewDidLoad() {
            super.viewDidLoad()
            
            //hide save button initially
            saveButton.isHidden = true
            //hide default navBar button mystock
            navigationItem.hidesBackButton = true
            
            // Set up the UI elements with the selected stock data
            if let selectedstock = selectedStock {
                stockName = selectedstock.name!
                stockNameLabel.text = selectedstock.name
                stockPriceLabel.text = "\(selectedstock.lastPrice)"
                statusSegmentControl.selectedSegmentIndex = Int(selectedstock.status)
                rankSelectedSlider.value = selectedstock.rank
                
            }
        }
    
    //Action for edit button for edit current stock details
    @IBAction func editPressed(_ sender: UIBarButtonItem) {
        saveButton.isHidden = false
    }
    
    //Action for status Segment to get the current index
    @IBAction func statusSegmentAction(_ sender: UISegmentedControl) {
        self.selectedStatus = Int16(sender.selectedSegmentIndex)
    }
    
    //Action for rankSlider to get the value for Rank
    @IBAction func rankSelectedSliderAction(_ sender: UISlider) {
        self.selectedRank = sender.value
    }
    
    
    //Action to perform save based on current status and rank selected to the Stock CoreData
    @IBAction func savePressed() {
        // Retrieve the AppDelegate and the viewContext for Core Data
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {return
        }
        
        let context = appDelegate.persistentContainer.viewContext
        
        // Create a fetch request for the "Stock" entity
        let fetchRequest: NSFetchRequest<Stock> = Stock.fetchRequest()
        
        // select stock with same name
        let predicate = NSPredicate(format: "name == %@", stockName)
        fetchRequest.predicate = predicate
        
    
        // Execute the fetch request
        do {
            let currentstock = try context.fetch(fetchRequest)
            for stock in currentstock{
                //Update the current status and rank to the current Stock entry
                stock.rank = selectedRank
                stock.status = selectedStatus
            }
            
            // Save to Core Data and dismiss the currentViewController
            do {
                try context.save()
                saveButton.isHidden = true
            } catch {
                print("Failed to save stock: \(error)")
            }
            
        } catch {
            print("Failed to fetch stock details: \(error.localizedDescription)")
        }
        
    }
    
    
}
