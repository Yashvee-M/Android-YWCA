//
//  HistoryViewController.swift
//  Senthilvadivu_Makileeshwaran_Assignment2_7
//
//  Created by user266706 on 11/15/24.
//

import UIKit

//Class to diplay history of products purchased
class HistoryViewController: UIViewController, UITableViewDataSource, UITableViewDelegate{

    //Outlet for TableView
    @IBOutlet weak var historyDisplayTableView: UITableView!
    
    //Dismiss current viewController
    @IBAction func DonePressed() {
        self.dismiss(animated: true, completion: nil)
    }
    
    //Loaded first when History View Controller starts
    override func viewDidLoad() {
        super.viewDidLoad()
        historyDisplayTableView.dataSource = self
        historyDisplayTableView.delegate = self

        // Do any additional setup after loading the view.
    }
    
    //set number of sections
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    //set number of rows
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return history.count
    }
    //set value for each cell
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "mycellid", for: indexPath) //for reuse of cell prototype
        print(indexPath.row)
        cell.textLabel?.text = "\(history[indexPath.row].quantity) × \(history[indexPath.row].name) TotalPrice:\(history[indexPath.row].price)"
        
        cell.detailTextLabel?.text = "\(history[indexPath.row].date)"
        return cell
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
