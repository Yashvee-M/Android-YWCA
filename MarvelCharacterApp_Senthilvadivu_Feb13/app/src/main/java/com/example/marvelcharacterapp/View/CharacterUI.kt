package com.example.marvelcharacterapp.View

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.marvelcharacterapp.FavoriteCharacterActivity
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel

//Character Screen to display list of character and SearchBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterUI(cvm : CharacterViewModel,
                onOneCharacterSelected: (Int,Int,String)->Unit){
    //get the current context
    val context = LocalContext.current
    //content of composable to display topBar, bottomBar and Content
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            //Display title
            TopAppBar(title = {
                Text(text = "Marvel Character List",
                    color = Color.White)
            },
                // Set background color of the TopAppBar
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Blue // Set background color
                ))
        },
        bottomBar = {
            //Footer to display copyright message from Composable FooterUI
            Row(modifier = Modifier.fillMaxHeight(0.09f)) {
                FooterUI() // This places the footer as a bottomBar
            }
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.height(0.05.dp))
            Row(
                modifier = Modifier.fillMaxHeight(0.25f)
                    .fillMaxWidth(0.95f).padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Icon to goto Favorite Character Activity
                IconButton(onClick = {
                    var intent = Intent(context,FavoriteCharacterActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Favorite, contentDescription = "tofav",modifier = Modifier.fillMaxSize(0.4f).size(48.dp),
                        Color.Blue)
                }
                //SearchBar to search Characters with 3 letters
                SearchBarUI(hint = "Search for Character(Enter 3 Letters)",
                    searchForCharacter = {
                    if(it.isEmpty()){
                        //cvm.setListEmpty()
                        //When searchQuery isEmpty it fetch the default characterList from Api
                        //cvm.getAllCharactersFromAPI(2,5)
                    }
                    else{
                        //set the previous state list empty
                        cvm.setListEmpty()
                        //when searchQuery with 3 characters it fetches characters begins with 3 letters from Api
                        cvm.getAllSearchedCharacter(2, 10, it)
                        }
                })

            }
            Row {
                //Display list of characters
                LazyColumn(modifier = Modifier.padding(5.dp))
                {
                    //for characters in characterStateList it will display characters one per Row
                    cvm.characterStateList.let {
                        items(count = it.size)
                        { index ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(5.dp).border(width = 1.dp,Color.Blue).clickable {

                                    onOneCharacterSelected(index,cvm.characterStateList[index].id,cvm.characterStateList[index].name)},
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.fillMaxWidth(0.25f)) {
                                    //Name of the character
                                    Text(it[index].name, fontSize = 20.sp, color = Color.Blue)
                                }
                                Column (modifier = Modifier.fillMaxWidth(0.75f).padding(5.dp)) {
                                    // Concatenate thumbnail- with extension to create URL
                                    val imageUrl =
                                        it[index].thumbnail + "." + it[index].thumbnailExt
                                    // Convert the image URL from http to https
                                    var secureImageUrl = imageUrl.replace("http://", "https://")
                                    //when no image found display default image
                                    if (secureImageUrl == "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg") {
                                        secureImageUrl = "https://wallpapers.com/images/featured/bte9zcsa9pvyzpvk.jpg"//"https://wallpaperaccess.com/full/1869513.jpg"
                                            //"https://wallpaperaccess.com/full/3363118.jpg"
                                    }
                                    //Load and display the thumbnail image using AsyncImage from Coil
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
                            }

                        }
                    }
                }
            }
        }
    }
}

