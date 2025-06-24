//
//  LoginViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/22/24.
//

import UIKit

class LoginViewController: UIViewController {
    var user : [User] = []
    
    
    //Username TextField outLet
    @IBOutlet weak var usernameText: UITextField!
    //Passqord TextField outlet
    @IBOutlet weak var passwordText: UITextField!
    
    //Message display Outlet
    @IBOutlet weak var messageLabel: UILabel!
    
    //Loads when LoginVC strats
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    
    //To add userInfo to the user object
    @IBAction func signUp() {
        let fileexist = checkIfFile(filename: "user_data.plist")
        if(!fileexist){
            
            guard let username = usernameText.text, let password = passwordText.text else { return }
            // Create a new user
            let newUser = User(uname : username, upassword: password)
            user.append(newUser)
            messageLabel.text = "User \(newUser.uname) signed up successfully. Please signin"
            usernameText.text = ""
            passwordText.text = ""
            saveUsersToPlist(user: user, filename: "user_data.plist")
            cu.name = username
        }
        else{
            loadUsersFromPlist(filename: "user_data.plist")
            guard let username = usernameText.text, let password = passwordText.text else { return }
            // Create a new user
            let newUser = User(uname : username, upassword: password)
            user.append(newUser)
            messageLabel.text = "User \(newUser.uname) signed up successfully. Please signin"
            
            usernameText.text = ""
            passwordText.text = ""
            
            saveUsersToPlist(user: user, filename: "user_data.plist")
            cu.name = username
        }
        
    }
        
        
    
    
    //To check whether the username and Password correct then allow user to takeQuiz and see Results
    @IBAction func signIn() {
        
        loadUsersFromPlist(filename: "user_data.plist")
        
        
        guard let username = usernameText.text, let password = passwordText.text else { return }
                
                // Check if user exists
                if let existingUser = user.first(where: { $0.uname == username && $0.upassword == password }) {
                    // Proceed to the next screen LandingVC
                    performSegue(withIdentifier: "LoginVCtoLanding", sender: existingUser.uname)
                } else {
                    messageLabel.text = "Invalid username or password"
                    usernameText.text = ""
                    passwordText.text = ""
                    
                }
    }
    
    
    //when segue happens pass the existingUser value to the Landing View Controller
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
            if segue.identifier == "LoginVCtoLanding" {
                if let landingVC = segue.destination as? LandingViewController {
                    // Pass the username to LandingViewController
                    if let username = sender as? String {
                        landingVC.username = username
                    }
                }
            }
        }
    
    
    
    
    
    
    // Encode the data to a property list (plist) format
    func saveUsersToPlist(user : [User], filename: String) {
        let encoder = PropertyListEncoder()
           
           // Specify the output format (XML)
           encoder.outputFormat = .xml
           
           do {
               // Encode the user array to data
               let data = try encoder.encode(user)
               
               // Get the path to the documents directory
               let fileManager = FileManager.default
               let documentsURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
               let plistURL = documentsURL.appendingPathComponent("user_data.plist")
               // Write the data to the .plist file
               try data.write(to: plistURL)
               print("Successfully saved to \(plistURL.path)")
               
           } catch {
               print("Error encoding or writing plist: \(error.localizedDescription)")
           }
    }
    
    
    func loadUsersFromPlist(filename: String) -> [User]? {
        let decoder = PropertyListDecoder()
        
        let fileManager = FileManager.default
        let documentsURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
        let plistURL = documentsURL.appendingPathComponent(filename)
        
        do {
            // Read the data from the plist file
            let data = try Data(contentsOf: plistURL)
            
            // Decode the data into an array of User objects
            user = try decoder.decode([User].self, from: data)
            return user
        } catch {
            print("Error reading or decoding plist: \(error.localizedDescription)")
            return nil
        }
    }
    
    func checkIfFile(filename: String)-> Bool{
        let fileManager = FileManager.default

        // Get the path to the Documents directory
        let documentsURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
        let filePath = documentsURL.appendingPathComponent(filename).path
           
           do {
               let attributes = try fileManager.attributesOfItem(atPath: filePath)
               if let isDirectory = attributes[.type] as? FileAttributeType {
                   if isDirectory == .typeDirectory {
                       return false  // It's a directory, not a file
                   }
               }
               return true
           } catch {
               print("Error checking file: \(error)")
               return false
           }

    }
    
    
        
}
