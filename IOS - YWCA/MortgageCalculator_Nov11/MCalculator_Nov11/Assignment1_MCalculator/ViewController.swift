//
//  ViewController.swift
//  Assignment1_MCalculator
//
//  Created by user266706 on 11/11/24.
//



import UIKit

//Structure to store history of records while doing calculation
struct Record{

    var purchasePrice: Float

    var downPayment: Float

    var interestRate: Float

    var loanValue : Float

    var repaymentTimeValue : Int

    var estimatedMonthlyValue : Float

    

}



class ViewController: UIViewController {

    //Outlet for List view of History of records
    @IBOutlet weak var savedListView: UIView!
    

    var records:[Record]=[]

    //Outlet for PurchacePrice value Label
    @IBOutlet weak var PurchasePrice: UILabel!
    
    //Outlet for DownPayment value Label
    @IBOutlet weak var DownPayment: UILabel!
    
    //Outlet for InterestRate value Label
    @IBOutlet weak var InterestRate: UILabel!
    
    //Outlet for Repayment value Label
    @IBOutlet weak var RepaymentTime: UILabel!
    
    
    //Outlet for LoanAmount value Label
    @IBOutlet weak var LoanAmount: UILabel!
    
    ////Outlet for EstimatedMonthly value Label
    @IBOutlet weak var EstimatedMonthly: UILabel!
   
    //Action for change of value in switch view button(Calculator and SavedList view
    @IBAction func switchViewButton(_ sender: UISegmentedControl) {
        
        let selectedIndex = sender.selectedSegmentIndex;
        
        if selectedIndex == 0{
            
            savedListView.isHidden = true
            
        }
        
        else{
            
            savedListView.isHidden = false
            
            showRecords()
            
        }
        
        
    }


    

    var PurchasePriceValue : Float = 0

    var DownPaymentValue : Float = 0

    var InterestRateValue : Float = 0

    var LoanValue : Float = 0

    var RepaymentTimeValue : Int = 20

    var EstimatedMonthlyValue : Float = 0

    

    

    override func viewDidLoad() {

        super.viewDidLoad()

        PurchasePrice.text = " $0 "

        DownPayment.text = " $0"

        InterestRate.text = " 0% "

        CalculateLoan()

        savedListView.isHidden=true

        // Do any additional setup after loading the view.

    }

    

    
    @IBAction func PurchasePriceSlider(_ sender: UISlider) {
    
        PurchasePrice.text = "$\(sender.value)"

        PurchasePriceValue = sender.value

        CalculateLoan()

        CalculateEstimatedMonthly()

    }

    

    
    @IBAction func DownPaymentSlider(_ sender: UISlider) {
    

        DownPayment.text = "$\(sender.value)"

        DownPaymentValue = sender.value

        CalculateLoan()

        CalculateEstimatedMonthly()

    }

    
    @IBAction func InterestRateSlider(_ sender: UISlider) {
    
        InterestRate.text = "\(sender.value)%"

        InterestRateValue = sender.value

        InterestRateValue = InterestRateValue/100/12

        CalculateEstimatedMonthly()

    }

    func CalculateLoan () {

        LoanValue = PurchasePriceValue - DownPaymentValue

        LoanAmount.text = "$\(LoanValue)"

    }

    

    @IBAction func RepaymentTime(_ sender: UISegmentedControl) {
   
        let selectedIndex = sender.selectedSegmentIndex;

        if let selectedTitle = sender.titleForSegment(at: selectedIndex) {

            RepaymentTime.text = selectedTitle + " Years "

            RepaymentTimeValue = Int (selectedTitle)!

            CalculateEstimatedMonthly()

        }

        

    }

    func CalculateEstimatedMonthly() {

        

        let InterestRateValue = (InterestRateValue/100)/12

            let RepaymentTimeValue = RepaymentTimeValue * 12

            let numerator = LoanValue * InterestRateValue  * pow( 1 + InterestRateValue, Float(RepaymentTimeValue))

            let denominator = pow( 1 + InterestRateValue, Float(RepaymentTimeValue)) - 1

        EstimatedMonthlyValue = numerator / denominator

        if(RepaymentTimeValue != 0 && InterestRateValue != 0 && DownPaymentValue != 0){

            EstimatedMonthly.text = "$\(EstimatedMonthlyValue)"

        }

        else{

            EstimatedMonthly.text = "$0"

        }

        

            //return denominator ! = 0? numerator / denominator : 0

       // }

    }

    @IBAction func saveRecord(_ sender: UIButton) {
   
        let newrecord = Record (purchasePrice: PurchasePriceValue, downPayment: DownPaymentValue, interestRate: InterestRateValue, loanValue: LoanValue, repaymentTimeValue: RepaymentTimeValue, estimatedMonthlyValue: EstimatedMonthlyValue)

        records.append(newrecord)

        let alert = UIAlertController(title: "Saved", message: "Mortgage calculation saved.", preferredStyle: .alert)

        alert.addAction(UIAlertAction(title: "OK", style: .default))

        present(alert, animated: true, completion: nil)

        

        

    }

    @IBOutlet weak var displayList: UITextView!
    
    
    func showRecords(){

        var display:String = ""

        

        for record in records{

            display += "\(record.downPayment),\(record.purchasePrice),\(record.interestRate)%  \(record.repaymentTimeValue) Years,Loan: \(record.loanValue),Estimated Monthly: \(record.estimatedMonthlyValue)\n \n"

        }

        if (display == ""){

            display="No Records Available"

        }

        

        print(display)

        displayList.text = display

    }

}



