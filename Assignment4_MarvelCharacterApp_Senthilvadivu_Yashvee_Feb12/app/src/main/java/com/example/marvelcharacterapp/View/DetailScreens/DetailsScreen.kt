package com.example.marvelcharacterapp.View.DetailScreens

import android.content.Intent
import androidx.compose.foundation.border
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
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel
import com.example.marvelcharacterapp.WebViewActivity

//Composable to display the Details of current character by characterID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailUI(modifier: Modifier,cvm : CharacterViewModel,selectedCharacterId:Int){
    //Current Context
    val context = LocalContext.current
    //Flag to display webView, initially no webView
    var webViewSelected by remember { mutableStateOf(false) }
    //hold SecureUrl to display webPage with https
    var secureUrl by remember { mutableStateOf("") }
    //hold title for URL
    var titleWebView by remember { mutableStateOf("") }
    //cvm.getCharacterById(2,5,selectedCharacterId.toString())
    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        //Check for empty response from Api
        if(cvm.characterIdStateList.isNotEmpty()){
            //Display header with title and HomeButton
            Row(modifier = Modifier.padding(5.dp)) {
                Header(cvm.characterIdStateList[0].name)
                //Text(cvm.characterIdStateList[0].name, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display image
                Row (modifier=Modifier.fillMaxHeight(0.3f)){
                //Concatenate the thumbnailPath with extension for url
                val imageUrl =
                    cvm.characterIdStateList[0].thumbnail + "." + cvm.characterIdStateList[0].thumbnailExt
                //Secure url with https
                val secureImageUrl =
                    imageUrl.replace("http://", "https://") // Replace http with https
                //Display AsyncImage from Coil compose
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(secureImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Character Thumbnail",
                    modifier = Modifier
                        .size(300.dp) // Set the size of the image
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop // Crop the image to maintain aspect ratio
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display description of Character
            Row (modifier=Modifier){
                Text(cvm.characterIdStateList[0].description, fontSize = 20.sp, color = Color.Blue,fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display last modified date
            Row {
                Text("last modified  :  ", fontSize = 20.sp, color = Color.Blue,fontWeight = FontWeight.Bold)
                Text(cvm.characterIdStateList[0].modified, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Display the details with URL
            LazyColumn {
                items(cvm.characterIdStateList[0].urlsURL.size){
                    Row(modifier = Modifier.fillMaxHeight(0.1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically){
                        //Title for Url
                        Text(cvm.characterIdStateList[0].urlsType[it]+"  :  ",
                            fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold)
                        Text(cvm.characterIdStateList[0].urlsURL[it], fontSize = 20.sp,
                            modifier = Modifier.clickable {
                                titleWebView = cvm.characterIdStateList[0].name+" : "+cvm.characterIdStateList[0].urlsType[it]
                                //Convert to secure Url with https
                                secureUrl = cvm.characterIdStateList[0].urlsURL[it].replace("http://", "https://") // Replace http with https
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
                intent.putExtra("title", titleWebView)
                context.startActivity(intent)
            }
        }

    }



}