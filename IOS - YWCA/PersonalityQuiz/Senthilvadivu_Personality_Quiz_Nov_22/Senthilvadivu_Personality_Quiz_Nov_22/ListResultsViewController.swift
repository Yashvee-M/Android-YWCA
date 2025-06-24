//
//  ListResultsViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/22/24.
//

import UIKit

class ListResultsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var tableViewResults: UITableView!
    
    
    //to Hold current user Previous result
    var currentResult : [UserResults] = []
    
 
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableViewResults.dataSource = self
        tableViewResults.delegate = self

        tableViewResults.reloadData()
        
        
        loadUsersFromPlist(filename: "result_data.plist")
        
        //Get current user previous Results where name = CurrentName
        for userresult in userResults{
            if userresult.name == cu.name{
                currentResult.append(userresult)
            }
        }
        
    }
    
    //Number of sections in TableView
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    //returns number of rows to TableView
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return currentResult.count
    }
    
    //Display each cell with Value Date and Teat Result
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "mycellid", for: indexPath)
        cell.textLabel?.text = "\(currentResult[indexPath.row].date)"
        cell.detailTextLabel?.text = "\(currentResult[indexPath.row].fresult)"
        
                
                return cell
    }
    
    
    //decode userdata file values to userResult object array
    func loadUsersFromPlist(filename: String) -> [UserResults]? {
        let decoder = PropertyListDecoder()
        
        let fileManager = FileManager.default
        let documentsURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
        let plistURL = documentsURL.appendingPathComponent(filename)
        
        do {
            // Read the data from the plist file
            let data = try Data(contentsOf: plistURL)
            
            // Decode the data into an array of User objects
            userResults = try decoder.decode([UserResults].self, from: data)
            return userResults
        } catch {
            print("Error reading or decoding plist: \(error.localizedDescription)")
            return nil
        }
    }
    
    
    

}
