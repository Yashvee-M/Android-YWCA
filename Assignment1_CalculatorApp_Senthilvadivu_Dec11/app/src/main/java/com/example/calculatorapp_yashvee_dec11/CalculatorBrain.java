package com.example.calculatorapp_yashvee_dec11;
import java.util.ArrayList;
import java.util.List;

//Contains all the required functions to calculate result
public class CalculatorBrain {

    private List<String> operands = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    
    private String input;

    //Function to push Operands, Operators,InputString to CalculatorBrain
    void push(List<String> operands, List<String> operators, String input){
        this.operands = operands;
        this.operators = operators;
        this.input = input;
    }

    //
    public Float calculate() {
        // First, process multiplication and division
        processOperatorPrecedence("*", "/");

        // Then, process addition and subtraction
        processOperatorPrecedence("+", "-");

        // The final result is the only operand left
        Float finalResult = Float.parseFloat(operands.get(0));

        // Record the operation in history
        String operationHistory = input + " = " + finalResult;
        MyApp.history.add(operationHistory);

        return finalResult;
    }


    // Perform calculations based on the given operator (+, -, *, /)
    //String... operatorsToProcess means that you can pass zero or more String arguments
    private void processOperatorPrecedence(String... operatorsToProcess) throws ArithmeticException {
        // Loop through operators in operands list
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);

            // Check if the operator is part of the current precedence
            if (isOperatorInPrecedence(operator, operatorsToProcess)) {
                // Get the operands at the current operator
                Float firstNumber = Float.parseFloat(operands.get(i));
                Float secondNumber = Float.parseFloat(operands.get(i + 1));

                // Perform the operation
                Float result = 0.0F;
                switch (operator) {
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        if (secondNumber == 0) {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        result = firstNumber / secondNumber;
                        break;
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + operator);
                }

                // Update the operands list with the result
                operands.set(i, String.valueOf(result));
                operands.remove(i + 1);

                // Remove the operator from the operators list
                operators.remove(i);

                // Adjust the index to recheck the new operands and operators
                i--;
            }
        }
    }
    // Helper function to check if an operator belongs to the current precedence
    private boolean isOperatorInPrecedence(String operator, String[] precedenceOperators) {
        for (String op : precedenceOperators) {
            if (op.equals(operator)) {
                return true;
            }
        }
        return false;
    }
    // Method to retrieve the history of calculations
    public String getHistoryAsString() {
        StringBuilder historyString = new StringBuilder();
        for (String historyEntry : MyApp.history) {
            historyString.append(historyEntry).append("\n");
        }
        return historyString.toString();
    }

}
