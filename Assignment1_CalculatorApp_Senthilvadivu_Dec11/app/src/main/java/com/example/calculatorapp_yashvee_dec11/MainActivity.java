package com.example.calculatorapp_yashvee_dec11;

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
    CalculatorBrain model = new CalculatorBrain();
    TextView calculateText;


    Button historyButton;
    TextView historyText;

    private List<String> operands = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private String currentInput = "";
    private String previousInput = "";

    private boolean isNewNumber = true;

    private String Input = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("calculator app","in onCreate Function");
        calculateText = findViewById(R.id.calculate_text);
        historyButton = findViewById(R.id.History);

        historyText = findViewById(R.id.HistoryTextView);
        // Hide the HistoryTextView when the button is pressed
        historyText.setVisibility(View.GONE);
        int[] digitIds = {R.id.Button0, R.id.Button1, R.id.Button2, R.id.Button3,
                R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7,
                R.id.Button8, R.id.Button9};
        for (int id : digitIds){
            findViewById(id).setOnClickListener(this::onDigitClicked);
        }
        findViewById(R.id.Buttonplus).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttonminus).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttonmultiply).setOnClickListener(this::onOperatorClicked);
        findViewById(R.id.Buttondivide).setOnClickListener(this::onOperatorClicked);

        //OnClickListener for clearButton
        findViewById(R.id.ButtonC).setOnClickListener(V -> {
            calculateText.setText("");
            Input = "";
            previousInput = "";
            operands.clear();
            operators.clear();
            isNewNumber = true;
        });

        findViewById(R.id.Buttonequal).setOnClickListener(V -> {

            if (isNewNumber){
                Toast.makeText(getApplicationContext(), "Can't calculate result", Toast.LENGTH_LONG).show();
            }
            else if (previousInput.equals("+") || previousInput.equals("-") || previousInput.equals("*") || previousInput.equals("/")) {
                Toast.makeText(getApplicationContext(), "Can't calculate result", Toast.LENGTH_LONG).show();
            }
            else{
                model.push(operands, operators, Input);
                Double result = model.calculate();
                String res = Double.toString(result);
                calculateText.setText(res);
                // Display the calculation history in the TextView
                updateHistory();
                Input = "";
                operands.clear();
                operators.clear();
                previousInput = "";

            }
        });

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
        else{
            Toast.makeText(getApplicationContext(), "One Digit number only!", Toast.LENGTH_LONG).show();
        }

    }

    // Handle operator button clicks
    private void onOperatorClicked(View view) {
        Button button = (Button) view;
        currentInput = button.getText().toString();
        if (previousInput.equals("+") || previousInput.equals("-") || previousInput.equals("*") || previousInput.equals("/")) {
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

    private void updateHistory() {
        // Get the history as a string from the calculator
        String history = model.getHistoryAsString();

        // Update the TextView to show the history
        historyText.setText(history);
    }

    public static boolean isValidNumber(String str) {
        try {
            Double.parseDouble(str);  // Try parsing as a Double
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}