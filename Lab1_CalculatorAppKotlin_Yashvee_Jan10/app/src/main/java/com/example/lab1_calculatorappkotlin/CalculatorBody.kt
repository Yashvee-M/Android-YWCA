package com.example.lab1_calculatorappkotlin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1_calculatorappkotlin.ui.theme.Lab1_CalculatorAppKotlinTheme


@Composable
fun CalculatorBody(modifier: Modifier = Modifier){

    Column(modifier = Modifier.fillMaxSize()) {
        UIPart()

    }
}


@Composable
fun UIPart(modifier:Modifier = Modifier){
    val calculatorBrain = CalculatorBrain()
    val input = remember { mutableStateOf("0") }
    val lastInput = remember { mutableStateOf("") }
    val historyList = remember { mutableStateListOf<String>() }
    val validOperators = setOf("+", "-", "*", "/")
    val historyButtonCLicked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(input.value)
        Spacer(modifier = Modifier.height(8.dp))
        Row (modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(onClick = {
                if (lastInput.value.isEmpty()) {
                    input.value = "1"
                    lastInput.value = "1"

                }
                else{
                    input.value += "1"
                    lastInput.value = "1"
                    }

            }) {
                Text("1")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()) {
                    input.value = "2"
                    lastInput.value = "2"
                }
                else{
                    input.value += "2"
                    lastInput.value = "2"
                }
            }) {
                Text("2")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()) {
                    input.value = "3"
                    lastInput.value = "3"

                }
                else{
                    input.value += "3"
                    lastInput.value = "3"
                }
            }) {
                Text("3")
            }
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                if ((lastInput.value in validOperators)||(lastInput.value.isEmpty()))
                    Toast.makeText(context, "Enter a Valid number!!", Toast.LENGTH_SHORT).show()
                else {
                    input.value += "+"
                    lastInput.value = "+"
                }

            }) {
                Text("+")
            }
        }
        Row (modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "4"
                    lastInput.value = "4"
                }
                else{
                    input.value += "4"
                    lastInput.value = "4"
                }
            }) {
                Text("4")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "5"
                    lastInput.value = "5"
                }
                else{
                    input.value += "5"
                    lastInput.value = "5"
                }
            }) {
                Text("5")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "6"
                    lastInput.value = "6"
                }
                else{
                    input.value += "6"
                    lastInput.value = "6"
                }
            }) {
                Text("6")
            }
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                if ((lastInput.value in validOperators)||(lastInput.value.isEmpty()))
                    Toast.makeText(context, "Enter a Valid number!!", Toast.LENGTH_SHORT).show()
                else {
                    input.value += "-"
                    lastInput.value = "-"
                }
            }) {
                Text("-")
            }
        }
        Row (modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "7"
                    lastInput.value = "7"
                }
                else{
                    input.value += "7"
                    lastInput.value = "7"
                }
            }) {
                Text("7")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "8"
                    lastInput.value = "8"
                }
                else{
                    input.value += "8"
                    lastInput.value = "8"
                }
            }) {
                Text("8")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "9"
                    lastInput.value = "9"
                }
                else{
                    input.value += "9"
                    lastInput.value = "9"
                }
            }) {
                Text("9")
            }
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                if ((lastInput.value in validOperators)||(lastInput.value.isEmpty()))
                    Toast.makeText(context, "Enter a Valid number!!", Toast.LENGTH_SHORT).show()
                else {
                    input.value += "*"
                    lastInput.value = "*"
                }
            }) {
                Text("*")
            }
        }
        Row (modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                input.value = "0"
                lastInput.value = ""
            }) {
                Text("C")
            }
            Button(onClick = {
                if (lastInput.value.isEmpty()){
                    input.value = "0"
                    lastInput.value = "0"
                }
                else{
                    input.value += "0"
                    lastInput.value = "0"
                }
            }) {
                Text("0")
            }
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                if(input.value == "0"||lastInput.value in validOperators){
                    Toast.makeText(context, "Invalid Input, Can't Calculate the result!!", Toast.LENGTH_SHORT).show()
                }
                else{
                    calculatorBrain.push(input.value)
                    val result: Float = calculatorBrain.calculate()
                    val res = result.toString()
                    historyList.add(input.value+"->"+res)
                    input.value = res
                    lastInput.value = ""
                    Log.d("history", historyList.toString())

                }
            }) {
                Text("=")
            }
            Button(colors = ButtonDefaults.buttonColors(Color.Gray),onClick = {
                if ((lastInput.value in validOperators)||(lastInput.value.isEmpty()))
                    Toast.makeText(context, "Enter a Valid number!!", Toast.LENGTH_SHORT).show()
                else {
                    input.value += "/"
                    lastInput.value = "/"
                }
            }) {
                Text("/")
            }
        }
        Button(onClick = {
            historyButtonCLicked.value = !historyButtonCLicked.value
        }) {
            Text("Show History")
        }
        if (historyButtonCLicked.value){
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.4f).padding(8.dp)
            ) {
                items(historyList.size) { index ->
                    val current = historyList[index]
                    Card(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth()
                            .background(Color.LightGray)
                    ) {
                        Text(current)

                    }
                }

            }
        }



    }

}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Lab1_CalculatorAppKotlinTheme {
        UIPart()
    }
}
