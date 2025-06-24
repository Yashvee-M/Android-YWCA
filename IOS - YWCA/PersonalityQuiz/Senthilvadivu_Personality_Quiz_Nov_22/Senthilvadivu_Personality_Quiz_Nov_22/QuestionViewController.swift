//
//  QuestionViewController.swift
//  Senthilvadivu_Personality_Quiz_Nov_22
//
//  Created by user266706 on 11/21/24.
//

import UIKit

class QuestionViewController: UIViewController {
    
    //Outlet to display each Question
    @IBOutlet weak var questionLabel: UILabel!
    
    //Outlets To display singleStack View with buttons
    @IBOutlet weak var singleStackView: UIStackView!
    @IBOutlet weak var singleButton1: UIButton!
    @IBOutlet weak var singleButton2: UIButton!
    @IBOutlet weak var singleButton3: UIButton!
    @IBOutlet weak var singleButton4: UIButton!
    
    
    //Outlets to display multiStackView with Labels and Switches and SubmitAnswer Button
    @IBOutlet weak var multipleStackView: UIStackView!
    @IBOutlet weak var multiLabel1: UILabel!
    @IBOutlet weak var multiSwitch1: UISwitch!
    @IBOutlet weak var multiLabel2: UILabel!
    @IBOutlet weak var multiSwitch2: UISwitch!
    @IBOutlet weak var multiLabel3: UILabel!
    @IBOutlet weak var multiSwitch3: UISwitch!
    @IBOutlet weak var multiLabel4: UILabel!
    @IBOutlet weak var multiSwitch4: UISwitch!

    
    //Outlets to display rangedStackView with slider and Labels and SubmitAnswerButton
    @IBOutlet weak var rangedStackView: UIStackView!
    @IBOutlet weak var rangedSlider: UISlider!
    @IBOutlet weak var rangedLabel1: UILabel!
    @IBOutlet weak var rangedLabel2: UILabel!
    
    
    //Oulet to display completed question progress
    @IBOutlet weak var questionProgressView: UIProgressView!
    
    //create quetions by create object to Question Struct
    var questions : [Question] = [
        Question(text: "Which food do you like the most?",
                 type: .single,
                 answers: [Answer(text: "Steak", type: .lion),
                           Answer(text: "Fish", type: .cat),
                           Answer(text: "Carrots", type: .rabbit),
                           Answer(text: "Corn", type: .turtle)
                          ]),
        Question(text: "Which activities do you enjoy?",
                 type: .multiple,
                 answers: [Answer(text: "Swimming", type: .turtle),
                           Answer(text: "Sleeping", type: .cat),
                           Answer(text: "Cuddling", type: .rabbit),
                           Answer(text: "Eating", type: .lion)
                          ]),
        Question(text: "How much do you enjoy car rides?",
                 type: .ranged,
                 answers: [Answer(text: "I dislike them", type: .cat),
                           Answer(text: "I get a little nervous", type: .rabbit),
                           Answer(text: "I barely notice them", type: .turtle),
                           Answer(text: "I love them", type: .lion)
                          ]),
        Question(text: "What is your favorite drink?",
                     type: .single,
                     answers: [Answer(text: "Coffee", type: .lion),
                               Answer(text: "Milk", type: .rabbit),
                               Answer(text: "Water", type: .turtle),
                               Answer(text: "Tea", type: .cat)
                     ]),
        Question(text: "Which outdoor activities do you enjoy?",
                     type: .multiple,
                     answers: [Answer(text: "Hiking", type: .turtle),
                               Answer(text: "Cycling", type: .lion),
                               Answer(text: "Fishing", type: .cat),
                               Answer(text: "Running", type: .rabbit)
                     ]),
        Question(text: "How much do you enjoy reading books?",
                  type: .ranged,
                  answers: [Answer(text: "I don't enjoy it", type: .cat),
                            Answer(text: "I like it sometimes", type: .rabbit),
                            Answer(text: "I enjoy it a lot", type: .lion),
                            Answer(text: "I love it!", type: .turtle)
                  ]),
        Question(text: "Which hobbies do you enjoy?",
                     type: .multiple,
                     answers: [Answer(text: "Painting", type: .rabbit),
                               Answer(text: "Reading", type: .cat),
                               Answer(text: "Cooking", type: .lion),
                               Answer(text: "Gardening", type: .turtle)
                     ])
    ]
    
    //Starts with question 0 keep track of each questions number
    var questionIndex = 0
    
    //Empty collection to store the players answers
    var answersChosen : [Answer] = []
    

    override func viewDidLoad() {
        super.viewDidLoad()
        updateUI()
        
    }
    
    
    @IBAction func singleAnswerButtonPressed(_ sender: UIButton) {
        let currentAnswers = questions[questionIndex].answers
        
        switch sender {
        case singleButton1:
            answersChosen.append(currentAnswers[0])
        case singleButton2:
            answersChosen.append(currentAnswers[1])
        case singleButton3:
            answersChosen.append(currentAnswers[2])
        case singleButton4:
            answersChosen.append(currentAnswers[3])
        default:
            break
        }
        nextQuestion()
    }
    
    
    @IBAction func multipleAnswerButtonPressed() {
        let currentAnswers = questions[questionIndex].answers
        
        if multiSwitch1.isOn{
            answersChosen.append(currentAnswers[0])
        }
        if multiSwitch2.isOn{
            answersChosen.append(currentAnswers[1])
        }
        if multiSwitch3.isOn{
            answersChosen.append(currentAnswers[2])
        }
        if multiSwitch4.isOn{
            answersChosen.append(currentAnswers[3])
        }
        nextQuestion()
    }
    
    @IBAction func rangedAnswerButtonPressed() {
        let currentAnswers = questions[questionIndex].answers
        //round slidervalue to an array index to get the current answer
        let index = Int(round(rangedSlider.value * Float(currentAnswers.count - 1)))
        
        answersChosen.append(currentAnswers[index])
        
        nextQuestion()
    }
    
    func nextQuestion(){
        questionIndex += 1
        
        if questionIndex < questions.count{
            updateUI()
        }
        else{
            performSegue(withIdentifier: "Results", sender: nil)
        }
    }
    
    
    
    //Update the questionStack between 3 different keytypes by disable others
    func updateUI(){
        
        //Initially hidden all 3 stackViews
        singleStackView.isHidden = true
        multipleStackView.isHidden = true
        rangedStackView.isHidden = true
        
        //Holds the currentQuestion from questions using questionIndex
        let currentQuestion = questions[questionIndex]
        
        //Holds the currentAnswer from currentQuestion
        let currentAnswers = currentQuestion.answers
        
        //Holds the current percentage of progress completed
        let totalProgress = Float(questionIndex) / Float (questions.count)
        
        //set the navigation title to current question number
        navigationItem.title = "Question #\(questionIndex + 1)"
        
        //Update questionLabel with current question
        questionLabel.text = currentQuestion.text
        
        //set the questionProgressView with currentProgress
        questionProgressView.setProgress(totalProgress, animated: true)
        
        //Update the question Stack with one of 3types from type of currentQuestion
        switch currentQuestion.type {
        case .single:
            updateSingleStack(using: currentAnswers)
        case .multiple:
            updateMultipleStack(using: currentAnswers)
        case .ranged:
            updateRangedStack(using: currentAnswers)
        }
    }
    
    @IBSegueAction func showResults(_ coder: NSCoder) -> ResultsViewController? {
        return ResultsViewController(coder: coder, responses: answersChosen)
    }
    
    //show the singleStack and Update singleStack with current Values
    func updateSingleStack(using answers : [Answer]){
        singleStackView.isHidden = false
        singleButton1.setTitle(answers[0].text, for: .normal)
        singleButton2.setTitle(answers[1].text, for: .normal)
        singleButton3.setTitle(answers[2].text, for: .normal)
        singleButton4.setTitle(answers[3].text, for: .normal)
    }
    
    
    //show the multiStack and Update with current Values
    func updateMultipleStack(using answers : [Answer]){
        multipleStackView.isHidden = false
        multiSwitch1.isOn = false
        multiSwitch2.isOn = false
        multiSwitch3.isOn = false
        multiSwitch4.isOn = false
        multiLabel1.text = answers[0].text
        multiLabel2.text = answers[1].text
        multiLabel3.text = answers[2].text
        multiLabel4.text = answers[3].text
    }
    
    //Show the rangedStack and update with current Values
    func updateRangedStack(using answers : [Answer]){
        rangedStackView.isHidden = false
        rangedSlider.setValue(0.5, animated: false)
        rangedLabel1.text = answers.first?.text
        rangedLabel2.text = answers.last?.text
    }
    

    

}
