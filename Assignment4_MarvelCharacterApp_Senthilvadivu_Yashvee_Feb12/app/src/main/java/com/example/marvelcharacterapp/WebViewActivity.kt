package com.example.marvelcharacterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.marvelcharacterapp.View.FooterUI
import com.example.marvelcharacterapp.View.WebViewScreen
import com.example.marvelcharacterapp.ui.theme.MarvelCharacterAppTheme

//To display webPage for current URL
class WebViewActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    //Executed when activity start
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get selectedUrl from DetailActivity
        val selectedUrl = intent.getStringExtra("selectedUrl")
        val title = intent.getStringExtra("title")
        enableEdgeToEdge()
        setContent {
            MarvelCharacterAppTheme {
                //Hold content of Activity
                Scaffold(modifier = Modifier.fillMaxSize(),
                    //TopBar to display title
                    topBar = {
                        TopAppBar(title = {
                            Row(modifier = Modifier,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "$title",
                                    color = Color.White)
                            }
                        },
                            // Set background color of the TopAppBar
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = Color.Blue // Set background color (purple in this case)
                            ))
                    },
                    //To go back to previous activity
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) {
                            Button(onClick = {
                                this.finish()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Close, // This is a built-in icon from Material Icons
                                    contentDescription = "Close Web View", // For accessibility
                                    modifier = Modifier
                                )
                            }
                        }
                    },
                    //Display footer at bottom of screen with copyRight content from FooterUI Composable
                    bottomBar = {
                        //Footer to display copyright message from Composable FooterUI
                        Row(modifier = Modifier.fillMaxHeight(0.09f)) {
                            FooterUI() // This places the footer as a bottomBar
                        }
                    }) { innerPadding ->
                    //null check
                    if (selectedUrl != null) {
                        //Display the webScreen composable with current URL
                        WebViewScreen(modifier = Modifier.padding(innerPadding),url = selectedUrl)
                    }
                }
            }
        }
    }
}


