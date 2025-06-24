//
//  LandiingViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/22/24.
//

import UIKit

class LandingViewController: UIViewController {

    @IBOutlet weak var displayLabel: UILabel!
    
    @IBOutlet weak var takeATestButton: UIButton!
    
    @IBOutlet weak var resultsButton: UIButton!
    
    @IBAction func unwindToQuizIntroduction(segue: UIStoryboardSegue) {
        
    }

    
    var username : String = ""

        override func viewDidLoad() {
            super.viewDidLoad()
            displayLabel.text = " Welcome \(username) !"
            cu.name = username

            takeATestButton.layer.borderColor = UIColor.blue.cgColor // Border color
            takeATestButton.layer.borderWidth = 2.0 // Border width
            takeATestButton.layer.cornerRadius = 10.0 // Rounded corners
            takeATestButton.layer.masksToBounds = true
            resultsButton.layer.borderColor = UIColor.blue.cgColor // Border color
            resultsButton.layer.borderWidth = 2.0 // Border width
            resultsButton.layer.cornerRadius = 10.0 // Rounded corners
            resultsButton.layer.masksToBounds = true
        }

}
