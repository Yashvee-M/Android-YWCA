//
//  Service.swift
//  Senthilvadivu_Assignment4_Stock_Nov28
//
//  Created by user266706 on 11/27/24.
//

import Foundation


class Service {
    
    //initializer for Service class
    private init(){}
    
    //Singleton object for Service class
    static var shared = Service()
    
    
    
    // Function to fetch selected stock data using RapidAPI
    func getData(fromURL sURL: String, completion : @escaping (Data)->()) {
        
        //header with key
        let headers = [
            "x-rapidapi-key": "72c3bf0585msh554dad757b4e8b6p1528d2jsn24a095060f7f",
            "x-rapidapi-host": "ms-finance.p.rapidapi.com"
        ]
        
        //make a request with URL
        let request = NSMutableURLRequest(url: NSURL(string: sURL)! as URL,
                                          cachePolicy: .useProtocolCachePolicy,
                                          timeoutInterval: 10.0)
        request.httpMethod = "GET"
        request.allHTTPHeaderFields = headers
        
        let session = URLSession.shared
        
        //dataTask to get a data from URLRequest
        let dataTask = session.dataTask(with: request as URLRequest, completionHandler: { (data, response, error) -> Void in
            if (error != nil) {
                print(error as Any)
            } else {
                guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else {
                            //print("Invalid HTTP response")
                            return
                        }
            }
            // Check if data is received
            if let data = data {
                completion(data)
            }
            else{
                //print("No data received")
                return
            }
        })
        dataTask.resume()
    }
}
