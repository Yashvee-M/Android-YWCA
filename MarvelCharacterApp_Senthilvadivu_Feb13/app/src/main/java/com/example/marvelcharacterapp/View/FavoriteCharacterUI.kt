package com.example.marvelcharacterapp.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marvelcharacterapp.Room.CharacterEntity

//Composable to display SearchBar and List of Favorite character from Database
@Composable
fun FavoriteCharacterUI(list: List<CharacterEntity>,
                        //Handle one Character Selected event
                        onOneCharacterSelected:(Int)->Unit,
                        //Handle one character to delete in DB event
                        oneCharacterToDelete:(CharacterEntity)->Unit){
    Row {
        //To display list of characters from DB
        LazyColumn(modifier = Modifier.padding(5.dp)) {
            items(count = list.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(5.dp).border(width = 1.dp, Color.Blue).clickable {
                        //To pass selectedId to favorite Character Activity
                        onOneCharacterSelected(list[index].id)
                    },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.25f)) {
                        //Display character name for each row
                        Text(list[index].name, fontSize = 20.sp, color = Color.Blue)
                    }
                    //display Image of character in each row
                    Column (modifier = Modifier.fillMaxWidth(0.55f).padding(5.dp)) {
                        //Concatenate thumbnailPath with extension to get URL
                        val imageUrl = list[index].thumbnail + "." + list[index].thumbnailExt
                        val secureImageUrl =
                            imageUrl.replace("http://", "https://") // Replace http with https
                        //Display async image with Coil
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
                    //Delete button to delete character from DB
                    Column (modifier = Modifier.fillMaxWidth(0.28f)) {
                        Button(onClick = {
                            //pass current character to delete from DB
                            oneCharacterToDelete(list[index])
                        }) {
                            //Icon for delete button
                            Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.background(Color.LightGray),
                                Color.Blue)
                        }
                    }

                }



            }
        }
    }
}