//
//  SearchResultTableViewController.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/27/24.
//

import UIKit

//created a protocol for searchViewController
protocol SearchResultTableViewControllerDelegate : AnyObject {
    func SearchResultTableViewControllerDidFinishWith (stock : TStock)
}

class SearchResultTableViewController: UITableViewController {
    
    //TStock object with property oberserver
    var stocksFromSearch : [TStock]? {
        didSet {
            //Reload table view when value for stocksFromSearch set
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        }
    }
    
    //delegate for searchResultTableViewController
    weak var delegate : SearchResultTableViewControllerDelegate?
    
    //get Loads when searchResultController starts
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        //return number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // return the number of rows
        return stocksFromSearch?.count ?? 0
    }

    //return cell to display information
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        //create reusablecell from identifier mycellid
        let cell = tableView.dequeueReusableCell(withIdentifier: "mycellid", for: indexPath)
        
        //get data for cell
        cell.textLabel?.text = stocksFromSearch?[indexPath.row].name
        
        return cell
    }
    
    //when the row selected it will get executed
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        //gets the current stock from stocksFromSearch using index as indexPath.row
        if let selectedStock = stocksFromSearch?[indexPath.row]{
            delegate?.SearchResultTableViewControllerDidFinishWith(stock: selectedStock)
        }
        
        
    }

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
