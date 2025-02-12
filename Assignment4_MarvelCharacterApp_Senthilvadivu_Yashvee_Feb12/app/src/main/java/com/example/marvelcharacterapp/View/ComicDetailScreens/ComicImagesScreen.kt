package com.example.marvelcharacterapp.View.ComicDetailScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marvelcharacterapp.View.Header
import com.example.marvelcharacterapp.ViewModel.ComicsViewModel

//Composable to display List of Promotional images in selected Comic by Id
@Composable
fun ComicImagesUI(modifier: Modifier, comicVM : ComicsViewModel, comicId:String){
    //Display the list of Images
    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(10.dp))
        //Check for empty response from Api
        if(comicVM.comicsStateList.isNotEmpty()){
            Row(modifier = Modifier.padding(5.dp)) {
                //Display the Comic Title with Home Button
                Header(comicVM.comicsStateList[0].title+" : "+"Promotional Images")
            }
            //List of Images
            LazyColumn {
                // Get the size of the list
                items(comicVM.comicsStateList[0].images.size) { index ->
                    // Get the secure URL with https
                    val secureImageUrl = comicVM.comicsStateList[0].images[index].replace("http://", "https://")

                    // Display Async Image from Coil Compose with secureUrl
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Make sure the image takes the full width of the screen
                            .padding(10.dp) // Padding around the image
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(secureImageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Comics Image",
                            modifier = Modifier
                                .fillMaxWidth() // Make the image take the full width of the screen
                                .height(300.dp) // Fixed height for each image
                                .clip(RoundedCornerShape(16.dp)), // Optional: Add rounded corners
                            contentScale = ContentScale.Crop // Crop the image to maintain aspect ratio
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp)) // Space between images
                }
            }

        }
    }
}