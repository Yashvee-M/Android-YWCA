package com.example.marvelcharacterapp

import androidx.compose.material.icons.Icons
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.marvelcharacterapp.Room.CharacterDatabase
import com.example.marvelcharacterapp.Room.CharacterEntity
import com.example.marvelcharacterapp.View.AlertUI
import com.example.marvelcharacterapp.View.FavoriteCharacterUI
import com.example.marvelcharacterapp.View.FooterUI
import com.example.marvelcharacterapp.View.SearchBarUI
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel
import com.example.marvelcharacterapp.ViewModel.MarvelAppRepository
import com.example.marvelcharacterapp.ViewModel.ViewModelFactory
import com.example.marvelcharacterapp.ui.theme.MarvelCharacterAppTheme

//Activity to display FavoriteList of Characters
class FavoriteCharacterActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    //OnCreate first when activity is called
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Create instance for Database, Dao
        val database = CharacterDatabase.getDBInstance(this)
        val cityDao = database.getCharacterDao()
        //Create instance for appRepo and ViewModel
        val appRepo = MarvelAppRepository(cityDao)
        val viewModelFactory = ViewModelFactory(appRepo)
        val cvm = ViewModelProvider(this,viewModelFactory)[CharacterViewModel::class.java]

        enableEdgeToEdge()
        //Boolean initially false to display whether the activity isInSearch or not
        var isInSearch by mutableStateOf(false)
        //Query for SearchBar, initially empty
        var searchQuery by mutableStateOf("")

        setContent {
            MarvelCharacterAppTheme {
                //Get context
                val context = LocalContext.current
                //Boolean to display alert or not, initially false
                val showAlert = remember { mutableStateOf(false) }
                //To hold current selected CharacterEntity of FavoriteList in Room database
                val selectedCharacter = remember { mutableStateOf(emptyList<CharacterEntity>()) }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    //TopBar to display title
                    topBar = {

                        TopAppBar(title = {
                            Row(modifier = Modifier,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Favorite Character List",
                                color = Color.White)
                                 }
                        },
                            // Set background color of the TopAppBar
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = Color.Blue // Set background color (purple in this case)
                            ))
                    },
                    //Display footer at bottom of screen with copyRight content from FooterUI Composable
                    bottomBar = {
                        //Footer to display copyright message from Composable FooterUI
                        Row(modifier = Modifier.fillMaxHeight(0.09f)) {
                            FooterUI() // This places the footer as a bottomBar
                        }
                    }) { innerPadding ->

                    Column(modifier = Modifier.padding(innerPadding)) {
                        //Create FlowList by getting List from RoomDatabase
                        var  dblist = if (!isInSearch) {
                            //If not isInSearch collect all characters from Database
                            cvm.getAllCharactersFromDB().collectAsState(initial = emptyList()).value
                        }else {
                            //If isInSearch collect characters according to searchQuery from SearchBar
                            cvm.searchForCharacter(searchQuery).collectAsState(initial = emptyList()).value
                        }
                        Row(
                            modifier = Modifier.fillMaxHeight(0.20f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = {
                                val intent = Intent(context,MainActivity::class.java)
                                context.startActivity(intent)
                            },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)){
                                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                            }
                            //Display SearchBar from Composable
                            SearchBarUI(hint = "Search For Characters in Favorite List") { searchText ->
                                //Make isInSearch Active by setting it to true and set searchQuery to get info from searchBar
                                isInSearch = true
                                searchQuery = searchText
                                ////have to implement Search by updating DbList
                            }
                        }
                        //Call Composable to display Favorite List according to selectedId
                        FavoriteCharacterUI(dblist,
                            onOneCharacterSelected = {id->
                            val selectedId = id
                            val intent = Intent(context,CharacterDetailActivity::class.java)
                            intent.putExtra("selectedId",selectedId)
                            context.startActivity(intent)
                        },
                            oneCharacterToDelete = {
                                // Delete Character from FavoriteList(DB )
                                selectedCharacter.value = selectedCharacter.value + it
                                // Show alert
                                showAlert.value = true
                                //cvm.deleteOneCharacterFromDB(it)
                            })
                        //Display alert if showAlert is set to true
                        if (showAlert.value){
                            //Call Composable AlertUI to display alert
                            AlertUI(message = "Do You Want To Delete From Favorite List?",
                                okButton = "Yes, Delete",
                                noButton = "NO, Don't Delete"
                                , onYes = {
                                    //Delete selectedCharacter From RoomDB by calling function from ViewModel
                                    cvm.deleteOneCharacterFromDB(selectedCharacter.value[0])
                                    // Set the selectedCharacter to empty again
                                    selectedCharacter.value = emptyList()
                                    //Set Alert to inActive
                                    showAlert.value = false
                                }, onNo = {
                                    // Set the selectedCharacter to empty again
                                    selectedCharacter.value = emptyList()
                                    showAlert.value = false
                                } )
                        }
                    }
                }
            }
        }

    }
}

