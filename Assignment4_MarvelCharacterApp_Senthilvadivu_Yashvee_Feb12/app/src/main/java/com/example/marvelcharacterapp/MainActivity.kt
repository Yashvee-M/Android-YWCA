package com.example.marvelcharacterapp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.marvelcharacterapp.Room.CharacterDatabase
import com.example.marvelcharacterapp.Room.CharacterEntity
import com.example.marvelcharacterapp.View.AlertUI
import com.example.marvelcharacterapp.View.CharacterUI
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel
import com.example.marvelcharacterapp.ViewModel.MarvelAppRepository
import com.example.marvelcharacterapp.ViewModel.ViewModelFactory
import com.example.marvelcharacterapp.ui.theme.MarvelCharacterAppTheme

//Home Screen display searchBar, character list and favorite screen icon
class MainActivity : ComponentActivity() {
    //OnCreate executed when activity begins
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Instance to create database, Dao and appRepo
        val database = CharacterDatabase.getDBInstance(this)
        val characterDao = database.getCharacterDao()
        val appRepo = MarvelAppRepository(characterDao)
        //Instance to create Character View Model
        val characterViewModelFactory = ViewModelFactory(appRepo)
        val cvm = ViewModelProvider(this,characterViewModelFactory)[CharacterViewModel::class.java]


        //Initially display characters from Marvel Api
        cvm.getAllCharactersFromAPI(5,20)
        setContent {
            //Content for Main Activity
            MarvelCharacterAppTheme {
                //Flag to  display alert box according to save character to DB
                val showAlert = remember { mutableStateOf(false) }
                //To hold the selectedIndex of character
                var selectedIndex by remember { mutableIntStateOf(0) }
                //To hold the current selected characterId to pass to next character detail activity
                var selectedId by remember { mutableIntStateOf(0) }
                //To hold the current selected characterName
                var selectedName by remember { mutableStateOf("") }
                //To hold current context
                val context = LocalContext.current

                Column{
                    //Calling the Character Screen to display list of character and SearchBar
                    CharacterUI(cvm,
                        onOneCharacterSelected = { index ,id,name->
                            showAlert.value = true
                            selectedIndex = index
                             selectedId = id
                            selectedName = name
                        })
                    //when showAlert flag become true it will display alert composable
                    if (showAlert.value){
                        AlertUI(message = "Do You Want To Save $selectedName to DB?",
                            okButton = "Yes, Save",
                            noButton = "NO, Don't Save"
                            , onYes = {
                                //Create characterEntity instance
                                val character = CharacterEntity(cvm.characterStateList[selectedIndex].id,
                                    cvm.characterStateList[selectedIndex].name,
                                    cvm.characterStateList[selectedIndex].description,
                                    cvm.characterStateList[selectedIndex].thumbnail,
                                    cvm.characterStateList[selectedIndex].thumbnailExt,
                                    cvm.characterStateList[selectedIndex].comicsName.toString())
                                //Insert new character to Database
                                val found = cvm.insertNewCharacterInDB(character)
                                //Character already exist display toast message
                                if (found){
                                    Toast.makeText(context,"Character Already in DataBase",Toast.LENGTH_SHORT).show()
                                }
                                //Intent to next character detail activity by passing extra selectedId
                                val intent = Intent(context,CharacterDetailActivity::class.java)
                                intent.putExtra("selectedId",selectedId)
                                context.startActivity(intent)
                                //Make showAlert flag again false
                                showAlert.value = false
                            }, onNo = {
                                //without saving character enter the character detail activity with selectedId
                                val intent = Intent(context,CharacterDetailActivity::class.java)
                                intent.putExtra("selectedId",selectedId)
                                context.startActivity(intent)
                                showAlert.value = false
                            } )
                    }

                }


            }


        }
    }
}



