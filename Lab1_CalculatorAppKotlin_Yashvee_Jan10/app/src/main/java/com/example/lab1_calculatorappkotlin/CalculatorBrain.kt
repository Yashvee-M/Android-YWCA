package com.example.lab1_calculatorappkotlin

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


//Contains all the required functions to calculate result
class CalculatorBrain {
    private var operands: MutableList<String> = ArrayList()
    private var operators: MutableList<String> = ArrayList()


    private var input: String? = null

    //Function to push Operands, Operators,InputString to CalculatorBrain
    fun push(input: String?) {
        val validOperators = setOf("+", "-", "*", "/")
        var operand = ""

        if (input != null) {
            for (char in input) {
                if (char.toString() !in validOperators) {
                    // Build the operand by appending the current character
                    operand += char
                } else if (char.toString() in validOperators) {
                    // When operator is found, add the current operand to operands list
                    if (operand.isNotEmpty()) {
                        operands.add(operand)
                        operand = "" // Reset the operand after adding it
                    }
                    // Add operator to the operators list
                    operators.add(char.toString())
                }
            }

            // Add the last operand after the loop finishes
            if (operand.isNotEmpty()) {
                operands.add(operand)
            }

            Log.d("input", "$operands  $operators")
        }
    }

    //
    fun calculate(): Float {
        // First, process multiplication and division
        processOperatorPrecedence("*", "/")

        // Then, process addition and subtraction
        processOperatorPrecedence("+", "-")

        // The final result is the only operand left
        val finalResult = operands[0].toFloat()

        // Record the operation in history
        val operationHistory = "$input = $finalResult"


        return finalResult
    }


    // Perform calculations based on the given operator (+, -, *, /)
    //String... operatorsToProcess means that you can pass zero or more String arguments
    @Throws(ArithmeticException::class)
    private fun processOperatorPrecedence(vararg operatorsToProcess: String) {
        // Loop through operators in operands list
        var i = 0
        while (i < operators.size) {
            val operator = operators[i]

            // Check if the operator is part of the current precedence
            if (isOperatorInPrecedence(operator, operatorsToProcess)) {
                // Get the operands at the current operator
                val firstNumber = operands[i].toFloat()
                val secondNumber = operands[i + 1].toFloat()

                // Perform the operation
                var result = 0.0f
                when (operator) {
                    "*" -> result = firstNumber * secondNumber
                    "/" -> {
                        if (secondNumber == 0f) {
                            throw ArithmeticException("Cannot divide by zero")
                        }
                        result = firstNumber / secondNumber
                    }

                    "+" -> result = firstNumber + secondNumber
                    "-" -> result = firstNumber - secondNumber
                    else -> throw IllegalStateException("Unexpected value: $operator")
                }

                // Update the operands list with the result
                operands[i] = result.toString()
                operands.removeAt(i + 1)

                // Remove the operator from the operators list
                operators.removeAt(i)

                // Adjust the index to recheck the new operands and operators
                i--
            }
            i++
        }
    }

    // Helper function to check if an operator belongs to the current precedence
    private fun isOperatorInPrecedence(
        operator: String,
        precedenceOperators: Array<out String>
    ): Boolean {
        for (op in precedenceOperators) {
            if (op == operator) {
                return true
            }
        }
        return false
    }

}