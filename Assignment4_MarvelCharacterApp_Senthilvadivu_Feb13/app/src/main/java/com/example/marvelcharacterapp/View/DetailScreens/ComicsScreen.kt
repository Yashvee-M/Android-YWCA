package com.example.marvelcharacterapp.View.DetailScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelcharacterapp.View.Header
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel

//Display List of comics which the character appear
@Composable
fun ComicsUI(cvm:CharacterViewModel,
             oneComicSelected:(String)->Unit){
    Column (modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Row{
            //Check for empty reponse from Api
            if(cvm.characterIdStateList.isNotEmpty()) {
                Row(modifier = Modifier.padding(5.dp)) {
                    //Display the header with character name as title and HomeButton
                    Header(cvm.characterIdStateList[0].name+"  :  ComicsList")
                }
            }
        }
        Row{
            //Display list of Comics
            LazyColumn(modifier = Modifier)
            {
                //Size of list
                items(count = cvm.characterIdStateList[0].comicsName.size)
                { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp).border(width = 1.dp, Color.Blue).clickable {
                        },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //display the comic Name
                        Text(cvm.characterIdStateList[0].comicsName[index], fontSize = 18.sp,
                            modifier = Modifier.padding(5.dp))
                        //Button to go to Comic detail View
                        Button(modifier = Modifier.padding(5.dp),onClick = {
                            val comicId = cvm.characterIdStateList[0].comicsURL[index].replace("http://gateway.marvel.com/v1/public/comics/","")
                            //Pass the comicId from Url to get the details of current Comic
                            oneComicSelected(comicId)
                        }) {
                            //Icon to go to comic Activity
                            Icon(
                                Icons.Default.ArrowForward, contentDescription = "GoToComicsDetail", modifier = Modifier.background(Color.LightGray),
                                Color.Blue)
                        }
                    }
                }
           }
        }
    }
}