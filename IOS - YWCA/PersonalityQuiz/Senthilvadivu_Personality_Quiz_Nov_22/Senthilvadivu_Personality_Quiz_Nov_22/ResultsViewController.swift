//
//  ResultsViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/21/24.
//

import UIKit

class ResultsViewController: UIViewController {
    //Outlet to display result emoji info
    @IBOutlet weak var resultAnswerLabel: UILabel!
    
    //Outlet display the definition of emoji
    @IBOutlet weak var resultDefinitionLabel: UILabel!
    
    
    
    //response from QuestionVC to calculate the result
    var responses: [Answer]
    
    //custom initializer for ResultViewController that satisfying the compiler
    init?(coder: NSCoder, responses: [Answer]){
        //coder 
        self.responses = responses
        super.init(coder: coder)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    //Load when ResulVC starts
    override func viewDidLoad() {
        super.viewDidLoad()
        //calculate result
        calculatePersonalityResult()
        navigationItem.hidesBackButton = true

    }
    
    
   
    
    
    //calculate Result from answers
    func calculatePersonalityResult(){
        let frequencyOfAnswers = responses.reduce(into: [:]){
            (counts, answer) in
            counts[answer.type, default: 0] += 1
        }
        
        let frequentAnswersSorted = frequencyOfAnswers.sorted(by:
            { (pair1, pair2) in
            return pair1.value > pair2.value
        })
        
        let mostCommonAnswer = frequentAnswersSorted.first!.key
        
        //combine above two line into following
        //let mostCommonAnswer = frequencyOfAnswers.sorted { $0.1 >
            //$1.1 }.first!.key
        
        resultAnswerLabel.text = "You are a \(mostCommonAnswer.rawValue)!"
        resultDefinitionLabel.text = mostCommonAnswer.definition
        //Current username
        let cuser = cu.name
        
        //get the CurrentTime
        let currentDateTime = Date()
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .medium
        let dateTimeString = formatter.string(from: currentDateTime)
        let testTime = " \(dateTimeString)"
        
        
        let result = "\(mostCommonAnswer.rawValue)"
        
    
        
        //check whether result file exist
        let fileexist = checkIfFile(filename: "result_data.plist")
        

        
        //if file not exist create file and the save the result data
        if(!fileexist){
            let newuserResult = UserResults(name: cuser, date: testTime, fresult: "\(mostCommonAnswer.rawValue)", results: [
                TestResults(result: responses[0].text),
                TestResults(result: responses[1].text),
                TestResults(result: responses[2].text),
                TestResults(result: responses[3].text),
                TestResults(result: responses[4].text),
                TestResults(result: responses[5].text),
                TestResults(result: responses[6].text)])
            userResults.append(newuserResult)
            saveResultsToPlist(result: userResults, filename: "result_data.plist")
            
        }
        //if file exist load previous results and the update current result and then archive
        else{
            //decode previous data from file
            loadUsersFromPlist(filename: "result_data.plist")
            let newuserResult = UserResults(name: cuser, date: testTime, fresult: "\(mostCommonAnswer.rawValue)", results: [
                TestResults(result: responses[0].text),
                TestResults(result: responses[1].text),
                TestResults(result: responses[2].text),
                TestResults(result: responses[3].text),
                TestResults(result: responses[4].text),
                TestResults(result: responses[5].text),
                TestResults(result: responses[6].text)])
            //append new record
            userResults.append(newuserResult)
            //Save current result values using encode
            saveResultsToPlist(result: userResults, filename: "result_data.plist")
            //Again load results with recent values using decoding
            loadUsersFromPlist(filename: "result_data.plist")
        }
        
    }
    
    
    
    
    // Encode the data to a property list (plist) format
    func saveResultsToPlist(result : [UserResults], filename: String) {
        let encoder = PropertyListEncoder()
           
           // Specify the output format (XML)
           encoder.outputFormat = .xml
           
           do {
               // Encode the user array to data
               let data = try encoder.encode(userResults)
               
               // Get the path to the documents directory
               let fileManager = FileManager.default
               let documentsURL = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first!
               let plistURL = documentsURL.appendingPathComponent("result_data.plist")
               // Write the data to the .plist file
               try data.write(to: plistURL)
               print("Successfully saved to \(plistURL.path)")
               
           } catch {
               print("Error encoding or writing plist: \(error.localizedDescription)")
           }
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
    
    
    //Check whether the file exist in the path
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
