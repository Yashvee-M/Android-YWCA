//
//  StockViewController.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/27/24.
//

import UIKit
import CoreData


class StockViewController: UIViewController {
    
    //Outlet for selected stock Label to show current stock
    @IBOutlet weak var selectedStockNameLabel: UILabel!
    
    //Outlet for Status Segment control to get status
    @IBOutlet weak var statusSegmentControl: UISegmentedControl!
    
    //Outlet for Rank slider to get Rank selected
    @IBOutlet weak var rankSelectedSlider: UISlider!

    //Outlet for saveButton
    @IBOutlet weak var saveStockButton: UIButton!
    
    //Outlet for selected Stack View
    @IBOutlet weak var selectedStockStackView: UIStackView!
    
    
    
    var selectedIndex : Int = 0//selected status
    var selectedRank : Float = 0//selected rank
    var stockName : String = ""//selectedStock name
    var performanceId: String = ""//performanceid of selected stock
    var isNewStock : Bool = true //Check selected stock is New or already exist
    var LastPrice : Float = 0
    
    //Get the SearchController
    lazy var searchResultTableVC = storyboard?.instantiateViewController(withIdentifier: "searchResultTVC") as! SearchResultTableViewController
    lazy var searchController = UISearchController(searchResultsController: searchResultTableVC)
    
    //Object for Stock coreData
    var stock : Stock?
    
    //Loads when StockViewController starts
    override func viewDidLoad() {
        super.viewDidLoad()
        //Initially set to hidden
        selectedStockStackView.isHidden = true
        
        if let stock = stock{
            //Stock is not nil
           //edit
        }
        else{
            //Stock is nill and Setting up ViewController
            navigationItem.searchController = searchController
            navigationItem.title = "Select the Stock to save"
            searchController.searchResultsUpdater = self
            searchResultTableVC.delegate = self
        }
    }
    
    
    //Action for StatusSegment control to get the Status Index
    @IBAction func statusSegmentAction(_ sender: UISegmentedControl) {
        self.selectedIndex = sender.selectedSegmentIndex
    }
    
    //Action for rankSlider to get the value for Rank
    @IBAction func rankSelectedSliderAction(_ sender: UISlider) {
        self.selectedRank = sender.value
    }
    
    //Action for saveButton to save StockData to the Stock coreData if it is new stock
    @IBAction func saveStockPressed() {
        
        //check whether the stock exist and set its isNewStock flag to true if exist
        fetchStockDetails()
        
        //If stock is new save it to CoreData
        if isNewStock{
           let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
           
           // Create new Stock object
           self.stock = Stock(context: context)
           
           // Set values for the stock entity
           self.stock?.setValue(stockName, forKey: "name")
           self.stock?.setValue(performanceId, forKey: "performanceid")
           self.stock?.setValue(selectedIndex, forKey: "status")
           self.stock?.setValue(selectedRank, forKey: "rank")
           self.stock?.setValue(LastPrice, forKey: "lastPrice")
           
           // Save to Core Data and dismiss the currentViewController
           do {
               try context.save()
               print("Stock saved successfully.")
               self.dismiss(animated: true, completion: nil)
               
           } catch {
               print("Failed to save stock: \(error)")
           }
       }
        
        //Executed if Stock is already exist and set isNewStock back to true
       else{
           selectedStockNameLabel.text = "Selected \(stockName) Already exist"
           title = "Select the Stock to save"
           isNewStock = true
           
       }
    }
    
    
    
    
    
    //fetchStockDetails from CoreData and check the stock already exist
    func fetchStockDetails(){
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
            let stocks = try context.fetch(fetchRequest)
            for st in stocks{
                if st.name == stockName{
                    isNewStock = false
                    break
                }
            }
        } catch {
            print("Failed to fetch stock details: \(error.localizedDescription)")
        }
    }
    
    
  
  
}
//End of StockViewController class



extension StockViewController : UISearchResultsUpdating{
    
    //Delegate for searchController when there is update in searchController value
    func updateSearchResults(for searchController: UISearchController) {
        
        //get the data from searchController
        guard let data = searchController.searchBar.text else{return}
        
        //get the URL by appending StockName to the URL to send the resquest for performanceId
        let urlstr = "https://ms-finance.p.rapidapi.com/market/v2/auto-complete?q=\(data)"
        print(urlstr)
        
        //call getData function to fetch data from rapid API
        Service.shared.getData(fromURL: urlstr) { [unowned self] data in
            
            //let st = String(data:data, encoding:.utf8)
            //print(st)
            
            //Decode the data as ResultVaues object using JSONDecoder
            if let value = try? JSONDecoder().decode(ResultValues.self, from: data){
                
                //Load value.results to stocksFromSearch which is TStock object
                self.searchResultTableVC.stocksFromSearch = value.results
                
            }
        }
    }
    
    func updateLastPrice(pid : String) {
        //get the URL by appending StockName to the URL to send the resquest for performanceId
        let urlstr = "https://ms-finance.p.rapidapi.com/stock/v2/get-realtime-data?performanceId=\(pid)"
        print(urlstr)
        //call getData function to fetch data from rapid API
        Service.shared.getData(fromURL: urlstr) { [unowned self] data in
            
            //let st = String(data:data, encoding:.utf8)
            //print(st)
            
            //Decode the data as ResultVaues object using JSONDecoder
            if let value = try? JSONDecoder().decode(ResultPrice.self, from: data){
                //print(value.lastPrice)
                LastPrice = value.lastPrice
                
            }
        }
        
    }
}


//Confirm Protocol searchViewControllerDelegate
extension StockViewController : SearchResultTableViewControllerDelegate{
    //func when searchViewController finish searching
    func SearchResultTableViewControllerDidFinishWith(stock: TStock) {
        
        //Set the title as stockName
        title = stock.name
        
        //make searchController inactive after searching done
        searchController.isActive = false
        
        //Show selected stackView show by setting isHidden flag to false
        selectedStockStackView.isHidden = false
        
        //store the selected stock name, performanceid to var stockName and performanceId
        self.stockName = stock.name
        self.performanceId = stock.performanceId
        updateLastPrice(pid: performanceId)
        
        //Update the selected Stock Name Label with current stockName
        selectedStockNameLabel.text = "\(stock.name)"
        
    }
    
   
    
}
