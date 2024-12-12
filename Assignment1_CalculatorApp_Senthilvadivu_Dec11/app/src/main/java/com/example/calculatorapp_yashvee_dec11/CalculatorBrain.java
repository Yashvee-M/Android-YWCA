package com.example.calculatorapp_yashvee_dec11;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorBrain {

    private List<String> operands = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    // List to track the history of calculations
    private List<String> history = new ArrayList<>();


    private String input;


    Double total = 0.0;
    void push(List<String> operands, List<String> operators, String input){
        this.operands = operands;
        this.operators = operators;
        this.input = input;
    }


    // Perform calculations based on the given operator (+, -, *, /)
    public Double calculate() {
        while (operands.size() > 1) {  // Continue until we have one operand left
            Double firstNumber = Double.parseDouble(operands.get(0));
            Double secondNumber = Double.parseDouble(operands.get(1));
            String opr = operators.get(0);

            operands.remove(0);  // Remove first operand
            operands.remove(0);  // Remove second operand
            operators.remove(0); // Remove operator

            Double result = 0.0;

            // Perform the appropriate operation based on the operator
            switch (opr) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + opr);
            }

            // Add the result back as the new first operand
            operands.add(0, String.valueOf(result));
        }
        // Record the operation in history
        String operationHistory = input + " = " + Double.parseDouble(operands.get(0));
        history.add(operationHistory);
        // The final result is the only operand left
        return Double.parseDouble(operands.get(0));
    }
    // Method to retrieve the history of calculations
    public String getHistoryAsString() {
        StringBuilder historyString = new StringBuilder();
        for (String historyEntry : history) {
            historyString.append(historyEntry).append("\n");
        }
        return historyString.toString();
    }

}
