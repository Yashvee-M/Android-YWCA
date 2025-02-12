package com.example.marvelcharacterapp.View.DetailScreens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelcharacterapp.View.Header
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel

//Display List of series in which the character appear
@Composable
fun SeriesUI(cvm: CharacterViewModel){
    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start){
        Row {
            //Check for empty reponse from Api
            if (cvm.characterIdStateList.isNotEmpty()) {
                Row(modifier = Modifier.padding(5.dp)) {
                    //Display the header with character name as title and HomeButton
                    Header(cvm.characterIdStateList[0].name + "  :  SeriesList")
                }
            }
        }
        Row {
            //Display list of Series
            LazyColumn(modifier = Modifier)
            {
                //Size of list
                items(count = cvm.characterIdStateList[0].seriesName.size)
                { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(10.dp).border(width = 1.dp, Color.Blue).clickable {},
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //display the series Name
                        Text(cvm.characterIdStateList[0].seriesName[index],
                            modifier = Modifier.padding(10.dp),fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}