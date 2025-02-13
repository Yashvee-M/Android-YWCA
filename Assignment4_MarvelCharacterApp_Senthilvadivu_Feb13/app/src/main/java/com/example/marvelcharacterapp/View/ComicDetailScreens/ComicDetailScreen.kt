package com.example.marvelcharacterapp.View.ComicDetailScreens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marvelcharacterapp.View.Header
import com.example.marvelcharacterapp.ViewModel.ComicsViewModel
import com.example.marvelcharacterapp.WebViewActivity

//Composable to display the Details of current comic by comicId
@Composable
fun ComicDetailUI(modifier: Modifier, comicVM : ComicsViewModel, comicId:String){
    //Current Context
    val context = LocalContext.current
    //Flag to display webView, initially no webView
    var webViewSelected by remember { mutableStateOf(false) }
    //hold SecureUrl to display webPage with https
    var secureUrl by remember { mutableStateOf("") }
    //hold title for URL
    var titleWebView by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(0.05.dp))
        //Check for empty response from Api
        if(comicVM.comicsStateList.isNotEmpty()){
            //Display header with title and HomeButton
            Row(modifier = Modifier.padding(5.dp)) {
                Header(comicVM.comicsStateList[0].title)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display image
            Row (modifier=Modifier.fillMaxHeight(0.3f)){
                //get image url and replace it with https
                val secureImageUrl =
                    comicVM.comicsStateList[0].thumbnail.replace("http://", "https://") // Replace http with https
                //Display AsyncImage from Coil compose
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(secureImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Comic Thumbnail",
                    modifier = Modifier
                        .size(300.dp) // Set the size of the image
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop // Crop the image to maintain aspect ratio
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display description of Comic
            Row (modifier=Modifier){
                Text(comicVM.comicsStateList[0].description, fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display last modified date
            Row {
                Text("last modified  :  ", fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                Text(comicVM.comicsStateList[0].modified, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display the details with URL
            LazyColumn {
                items(comicVM.comicsStateList[0].urlsURL.size){
                    Row(modifier = Modifier.fillMaxHeight(0.1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        //Title for Url
                        Text(comicVM.comicsStateList[0].urlsType[it]+"  :  ",
                            fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                       //Convert to secure Url with https
                        secureUrl =
                            comicVM.comicsStateList[0].urlsURL[it].replace("http://", "https://") // Replace http with https
                        titleWebView = comicVM.comicsStateList[0].title+" : "+comicVM.comicsStateList[0].urlsType[it]
                        //Clickable Url Text for webView
                        Text(secureUrl, fontSize = 20.sp,
                            modifier = Modifier.clickable {
                                // Display WebView by setting flag to true
                                webViewSelected = true

                            },
                            color = Color.Magenta,
                            textDecoration = TextDecoration.Underline)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            //If webView selected go to the webView activity
            if(webViewSelected) {
                //Again set back the webView flag to false
                webViewSelected = false
                //Create intent to goto WebView activity
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("selectedUrl",secureUrl)
                intent.putExtra("title",titleWebView)
                context.startActivity(intent)
            }
        }
    }
}


