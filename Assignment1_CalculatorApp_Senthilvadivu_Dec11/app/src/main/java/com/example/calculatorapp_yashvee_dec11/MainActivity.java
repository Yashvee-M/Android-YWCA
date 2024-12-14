package com.example.calculatorapp_yashvee_dec11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Object for CalculatorBrain Class
    CalculatorBrain model = new CalculatorBrain();
    //TextView to display inputs and result
    TextView calculateText;
    //Button Take user to SecondActivity where it displays History
    Button historySecondActivity;
    //To display history in same page
    Button historyButton;
    //TextView to display history
    TextView historyText;
    //ArrayList to get all operands entered by user
    private List<String> operands = new ArrayList<>();
    //Arraylist to get all operators entered by user
    private List<String> operators = new ArrayList<>();
    //To record current entered input
    private String currentInput = "";
    //To record previous input
    private String previousInput = "";
    //Check weather the number is New, Initially set to true
    private boolean isNewNumber = true;
    //String to get all the inputs to display it in to display calculateText
    private String Input = "";


    //On create function to initiate the Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("calculator app","in onCreate Function");

        //Set the id for caculate_text,History,HistorySecondActivity,HistoryTextView
        calculateText = findViewById(R.id.calculate_text);
        historyButton = findViewById(R.id.History);
        historySecondActivity = findViewById(R.id.HistorySecondActivity);
        historyText = findViewById(R.id.HistoryTextView);

        // Hide the HistoryTextView when the button is pressed
        historyText.setVisibility(View.GONE);

        //Set the id's for digitButtons
        int[] digitIds = {R.id.Button0, R.id.Button1, R.id.Button2, R.id.Button3,
                R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7,
                R.id.Button8, R.id.Button9};

        //OnClick event for digitId's pass perform onDigitClicked function for each digit pressed
        for (int id : digitIds){
            findViewById(id).setOnClickListener(this::onDigitClicked);
        }

        //Set onClickListener for each operator and perform onOperatorClicked function
        findViewById(R.id.Buttonplus).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttonminus).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttonmultiply).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttondivide).setOnClickListener(this::onOperatorClicked);

        //OnClickListener for clearButton to clear all input
        findViewById(R.id.ButtonC).setOnClickListener(V -> {
            calculateText.setText("");
            Input = "";
            previousInput = "";
            operands.clear();
            operators.clear();
            isNewNumber = true;
        });

        //Set OnclickListener for Equal Button to calculate result from CalculatorBrain
        //Update History for each calculation
        findViewById(R.id.Buttonequal).setOnClickListener(V -> {
            //If newNumber and PreviousInput is operator can't calculate result
            if (isNewNumber){
                Toast.makeText(getApplicationContext(), "Can't calculate result", Toast.LENGTH_LONG).show();
            }
            else if (previousInput.equals("+") || previousInput.equals("-") || previousInput.equals("*") || previousInput.equals("/")) {
                Toast.makeText(getApplicationContext(), "Can't calculate result", Toast.LENGTH_LONG).show();
            }

            else{
                //if input is empty can't calculate else calculate result and update history
                if (Input == "") {
                    Toast.makeText(getApplicationContext(), "Can't calculate result", Toast.LENGTH_LONG).show();
                }
                else{
                    model.push(operands, operators, Input);
                    Float result = model.calculate();
                    String res = Float.toString(result);
                    calculateText.setText(res);
                    // Display the calculation history in the TextView
                    updateHistory();
                    Input = "";
                    operands.clear();
                    operators.clear();
                    previousInput = "";
                }

            }
        });

        //SetOnclick Listener for display history in SecondActivity
        historySecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tosecond = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(tosecond);
            }
        });

        //Set OnclickListener for history button with Advance option
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = (String) historyButton.getText();
                if(Objects.equals(msg, "ADVANCE - WITH HISTORY")){
                    // Show the TextView when the button is pressed
                    historyText.setVisibility(View.VISIBLE); // Or View.INVISIBLE
                    historyButton.setText(R.string.historyTextAfterClick);
                }
                else{
                    // Show the TextView when the button is pressed
                    historyText.setVisibility(View.GONE); // Or View.INVISIBLE
                    historyButton.setText(R.string.historytext);
                }

            }
        });
    }

    // Handle digit button clicks
    private void onDigitClicked(View view) {
        Button button = (Button) view;
        currentInput = button.getText().toString();

        //previous number is not a valid number add currentInput to operands
        if (!isValidNumber(previousInput)||previousInput == ""){
            if (isNewNumber) {
                operands.add(currentInput);
                Input += currentInput;
                previousInput = currentInput;
                calculateText.setText(Input);
                isNewNumber = false;
            }
            else{
                operands.add(currentInput);
                Input += currentInput;
                previousInput = currentInput;
                calculateText.setText(Input);
            }
        }

        //PreviousInput is number Concatenate currentInput to previousInput
        else if (isValidNumber(previousInput)){
            int lastIndex = operands.size()-1;
            previousInput += currentInput;
            operands.set(lastIndex, previousInput);
            Input += currentInput;
            calculateText.setText(Input);
        }


        else{
            Toast.makeText(getApplicationContext(), "One Digit number only!", Toast.LENGTH_LONG).show();
        }
        Log.d("Operands","Values"+ operands.toString());

    }

    // Handle operator button clicks
    private void onOperatorClicked(View view) {
        Button button = (Button) view;
        currentInput = button.getText().toString();

        //If previousInput is operator or "" then display error message, otherwise add the operator to operators array
        if (previousInput.equals("+") || previousInput.equals("-") || previousInput.equals("*") || previousInput.equals("/")||previousInput.equals("")) {
            Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_LONG).show();
        }
        else if (isNewNumber){
            Toast.makeText(getApplicationContext(), "Start Should be Number", Toast.LENGTH_LONG).show();
        }
        else{
            operators.add(currentInput);
            Input += currentInput;
            calculateText.setText(Input);
            previousInput = currentInput;
        }

    }

    //Function to get History as String from CalculatorBrain and update History TextView
    private void updateHistory() {
        // Get the history as a string from the calculator
        String history = model.getHistoryAsString();

        // Update the TextView to show the history
        historyText.setText(history);
    }

    //Function to check whether the PreviousInput is valid number by try to convert into Double
    public static boolean isValidNumber(String str) {
        try {
            Double.parseDouble(str);  // Try parsing as a Double
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}