package com.example.marvelcharacterapp.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//To display Alert message used in MainActivity and FavoriteCharacter Activity
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertUI(message: String,
                    okButton: String,
                    noButton: String ,
                    onYes: ()->Unit,
                    onNo: ()->Unit ){
    //Create alert dialog
    AlertDialog(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp),
        //Handle on dismiss request
        onDismissRequest = {},
        ) {
        Column {
            //display message, yes and no button
            Text(message,
                fontSize = 20.sp,
                color = Color.Black,  // Message text color
                style = MaterialTheme.typography.bodyMedium)
            Row (modifier = Modifier
                .padding(top = 16.dp)  // Padding between buttons
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    onYes()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                    Text(okButton,
                        color = Color.White )
                }
                Button(onClick = {
                    onNo()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                    Text(noButton,
                        color = Color.White )
                }
            }
        }

    }
}