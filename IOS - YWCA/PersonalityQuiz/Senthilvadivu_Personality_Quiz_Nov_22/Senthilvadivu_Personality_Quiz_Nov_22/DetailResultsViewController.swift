//
//  DetailResultsViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/22/24.
//

import UIKit

class DetailResultsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var detailedTableView: UITableView!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        detailedTableView.dataSource = self
        detailedTableView.delegate = self
        

    
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return questions.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "mycellid", for: indexPath)
        cell.textLabel?.text = "\(questions[indexPath.row].text)"
        cell.detailTextLabel?.text = "\(results[indexPath])"
        
                
                return cell
    }
    

    

}
