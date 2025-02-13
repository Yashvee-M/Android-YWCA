package com.example.marvelcharacterapp.View.ComicDetailScreens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelcharacterapp.View.Header
import com.example.marvelcharacterapp.ViewModel.ComicsViewModel

//Composable to display List of Creators in selected Comic by Id
@Composable
fun ComicCreatorsUI(modifier: Modifier, comicVM : ComicsViewModel, comicId:String){
    //Display the list of Creators
    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(0.05.dp))
        //Check for empty response from Api
        if(comicVM.comicsStateList.isNotEmpty()){
            Row(modifier = Modifier.padding(5.dp)) {
                //Display the Comic Title with Home Button
                Header(comicVM.comicsStateList[0].title+" : "+"Creators")
            }
            //List of Creators
            LazyColumn(modifier=Modifier.padding(5.dp).border(width = 1.dp, color = Color.Blue),
                verticalArrangement = Arrangement.SpaceEvenly) {
                //get size of list
                items(comicVM.comicsStateList[0].creatorsName.size){
                    Row(modifier = Modifier.fillMaxHeight(0.2f).padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically){
                        Row(modifier=Modifier.padding(5.dp)){
                            //Display the current creators name
                            Text(comicVM.comicsStateList[0].creatorsName[it]+"  :  ", fontSize = 20.sp)
                            //Display the current Creator Role
                            Text(comicVM.comicsStateList[0].creatorsRole[it], fontSize = 20.sp, color = Color.Blue)
                        }

                    }
                    Spacer(modifier=Modifier.height(10.dp))
                }
            }
        }
    }
}