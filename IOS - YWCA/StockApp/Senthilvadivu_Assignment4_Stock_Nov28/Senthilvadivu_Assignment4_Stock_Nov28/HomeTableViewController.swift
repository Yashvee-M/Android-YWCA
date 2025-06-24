//
//  HomeTableViewController.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/27/24.
//
import UIKit
import CoreData

class HomeTableViewController: UITableViewController, NSFetchedResultsControllerDelegate {
    
    
    // Unwind segue action
       @IBAction func unwindToHome(_ sender: UIStoryboardSegue) {
           // This method will be called when the unwind segue is triggered
           
           // refreshing the table view
           tableView.reloadData()  // Example: reload the table view if necessary
       }
    
    
    var fetchedResultsController: NSFetchedResultsController<Stock>!
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Update current stock lastPrice from API
        updateStockPrices()
        
        // Set up the fetched results controller
        setupFetchedResultsController()
        
        // Set the table view data source and delegate
        tableView.delegate = self
        tableView.dataSource = self
        
        // Add Edit button to the navigation bar
        navigationItem.rightBarButtonItem = editButtonItem
    }

    // MARK: - Setting up NSFetchedResultsController

    func setupFetchedResultsController() {
        
        //setup a fetch request for Stock entity
        let fetchRequest: NSFetchRequest<Stock> = Stock.fetchRequest()
        
        //Add a sortDescriptor for status attribute
        let sortDescriptor = NSSortDescriptor(key: "status", ascending: true)
        fetchRequest.sortDescriptors = [sortDescriptor]
        
        //setup fetchResultController with request, context, section(status)
        fetchedResultsController = NSFetchedResultsController(fetchRequest: fetchRequest,
                                                               managedObjectContext: context,
                                                               sectionNameKeyPath: "status",
                                                               cacheName: nil)
        //Assign HomeVC object self to fetchResultController delegate
        fetchedResultsController.delegate = self
        
        //perform fetch within try
        do {
            try fetchedResultsController.performFetch()
        } catch {
            print("Failed to fetch data: \(error.localizedDescription)")
        }
    }
    
    // MARK: - Update Stock Prices
        
        func updateStockPrices() {
            
            // Get the managed context (assuming 'context' is your NSManagedObjectContext)
            //let context = persistentContainer.viewContext

            // Create a fetch request for the 'Item' entity
            let fetchRequest: NSFetchRequest<Stock> = Stock.fetchRequest()

            do {
                // Fetch all the items
                let stocks = try context.fetch(fetchRequest)
                
                // Iterate over all stocks and update their lastPrice from the API
                for stock in stocks {
                    guard let performanceId = stock.performanceid else { continue }
                    updateLastPrice(for: stock, with: performanceId)
                }
                
                // Save the context to persist the changes
                try context.save()
                print("Successfully updated all items")
            } catch {
                // Handle any errors
                print("Error updating items: \(error)")
            }
           
        }
        
        //Update lastPrice from MS finance - rapidAPI
        func updateLastPrice(for stock: Stock, with performanceId: String) {
            
            // Create the URL to fetch data from the API
            let urlstr = "https://ms-finance.p.rapidapi.com/stock/v2/get-realtime-data?performanceId=\(performanceId)"
            
            // Call the API to get the stock's last price
            Service.shared.getData(fromURL: urlstr) { [weak self] data in
                guard let self = self else { return }

                // Decode the fetched data into a ResultPrice object
                if let value = try? JSONDecoder().decode(ResultPrice.self, from: data) {
                    // Update the stock's lastPrice with the fetched data
                    stock.lastPrice = value.lastPrice
                    
                    // Save the updated stock object in Core Data
                    do {
                        try self.context.save()
                        
                        // Reload the table view to display the updated price
                        DispatchQueue.main.async {
                            self.tableView.reloadData()
                        }
                    } catch {
                        print("Failed to save updated stock: \(error)")
                    }
                } else {
                    print("Failed to fetch stock data for performanceId: \(performanceId)")
                }
            }
        }


    // MARK: - Table View Data Source
    
    //return number of sections
    override func numberOfSections(in tableView: UITableView) -> Int {
           return fetchedResultsController.sections?.count ?? 0
       }
    
    //set a number of rows for each section
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
            guard let sectionInfo = fetchedResultsController.sections?[section] else {
                return 0
            }
            return sectionInfo.numberOfObjects
        }
    
    //set title for each section based on status 0 or 1
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
            guard let sectionInfo = fetchedResultsController.sections?[section] else {
                return nil
            }
            if sectionInfo.name == "0"{
                return "Active List"
            }
            else{
                return "Watch List" // This corresponds to the 'status' value
            }
        }
    
    //get data for each cell and return cell for each row
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let stock = fetchedResultsController.object(at: indexPath)
        
        //reusable CustomTableView cell with nameLabel, priceLabel, rankSlider, rankLabel
        let cell = tableView.dequeueReusableCell(withIdentifier: "StockCell", for: indexPath) as! CustomTableViewCell
        
        //display stockName
        cell.nameLabel.text  = stock.name
        
        //display currentPrice
        cell.priceLabel.text = "\(stock.lastPrice)"
        
        //display rank value(0 to 1 for cold, hot, veryHot)
        cell.rankSlider.value = stock.rank
        
        //get a description for rank based on its value
        let rankvalue = rank.description(for: stock.rank)
        
        //display rank description
        cell.rankLabel.text = "\(rankvalue)"
        
        
        //cell.detailTextLabel?.text = "\(stock.lastPrice)"
        return cell
    }
    

    // MARK: - Table View Editing

    //  Allow editing (deleting and reordering)
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return true if you want to allow editing for a specific row (e.g., all rows)
        return true
    }

    // Handle deletion of a row
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let stockToDelete = fetchedResultsController.object(at: indexPath)
            
            // Delete the stock object from the context
            context.delete(stockToDelete)
            
            // Save the context to persist the change
            do {
                try context.save()
            } catch {
                print("Failed to delete stock: \(error)")
            }
        }
    }

    //  Allow reordering of rows
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        return true // Allow reordering of rows
    }

    //  Handle reordering rows
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
        // Reordering of rows is handled here. You can update the data model if needed.
    }

    
    
    //MARK: - didSelectRowAt for getting current selectedStock object
    
    //get the selected stock object and pass it to the StockDetailsVC
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        // Deselect the row after selection (optional)
        tableView.deselectRow(at: indexPath, animated: true)
        
        // Get the selected stock
        let selectedStock = fetchedResultsController.object(at: indexPath)
        
        // Perform the segue to navigate to the next view controller
        performSegue(withIdentifier: "showStockDetail", sender: selectedStock)
    }
    
    //Loads the Current Stock object details to the destination Stock object selectedStock
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showStockDetail" {
            // Get the destination view controller
            if let destinationVC = segue.destination as? StockDetailViewController {
                // Pass the selected stock to the destination view controller
                if let stock = sender as? Stock {
                    destinationVC.selectedStock = stock
                }
            }
        }
    }
    // MARK: - NSFetchedResultsController Delegate Methods

    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange anObject: Any, at indexPath: IndexPath?, for type: NSFetchedResultsChangeType, newIndexPath: IndexPath?) {
        switch type {
        case .insert:
            if let newIndexPath = newIndexPath {
                tableView.insertRows(at: [newIndexPath], with: .automatic)
            }
        case .delete:
            if let indexPath = indexPath {
                tableView.deleteRows(at: [indexPath], with: .automatic)
            }
        case .update:
            if let indexPath = indexPath {
                tableView.reloadRows(at: [indexPath], with: .automatic)
            }
        case .move:
            if let indexPath = indexPath, let newIndexPath = newIndexPath {
                tableView.moveRow(at: indexPath, to: newIndexPath)
            }
        @unknown default:
            break
        }
    }
    
    // Optional: If you have sections, you can handle section changes
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange sectionInfo: NSFetchedResultsSectionInfo, atSectionIndex sectionIndex: Int, for type: NSFetchedResultsChangeType) {
        switch type {
        case .insert:
            tableView.insertSections(IndexSet(integer: sectionIndex), with: .automatic)
        case .delete:
            tableView.deleteSections(IndexSet(integer: sectionIndex), with: .automatic)
        default:
            break
        }
    }
    


}
