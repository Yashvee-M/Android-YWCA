package com.example.marvelcharacterapp.View

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelcharacterapp.MainActivity

//To display Header message for each screen to get String for title
@Composable
fun Header(title: String) {
    //Current context
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxHeight(0.1f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        //Display button to goto Home Activity
        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)){
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        }
        //Display Text as Title
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge, // Default typography style for headers
            color = Color.Blue, // You can customize the color
            modifier = Modifier.padding(10.dp),// Add padding around the title
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

}

//To display footer for each screen
@Composable
fun FooterUI() {
    //Box to display copyright message on each activity
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            //.align(LineHeightStyle.Alignment.BottomCenter)

    ) {
        // Create a footer with a simple Row
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "© 2025 MARVEL. All copyrights reserved.", style = MaterialTheme.typography.bodySmall, color = Color.Blue,
                fontWeight = FontWeight.Bold)
        }
    }
}