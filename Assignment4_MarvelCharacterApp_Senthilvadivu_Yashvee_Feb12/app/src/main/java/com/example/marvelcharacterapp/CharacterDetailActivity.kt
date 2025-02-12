package com.example.marvelcharacterapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.marvelcharacterapp.DetailNavigation.MyNavGraph
import com.example.marvelcharacterapp.DetailNavigation.NavItem
import com.example.marvelcharacterapp.Room.CharacterDatabase
import com.example.marvelcharacterapp.View.FooterUI
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel
import com.example.marvelcharacterapp.ViewModel.MarvelAppRepository
import com.example.marvelcharacterapp.ViewModel.ViewModelFactory
import com.example.marvelcharacterapp.ui.theme.MarvelCharacterAppTheme

//Detailed view of Selected Character by CharacterId, Fetch information from MarvelApi
class CharacterDetailActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    //OnCreate first when activity is called
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get selectedId from MainActivity
        val selectedId = intent.getIntExtra("selectedId",0)
        Log.d("Value",selectedId.toString())

        //Create instance for Database, Dao
        val database = CharacterDatabase.getDBInstance(this)
        val characterDao = database.getCharacterDao()

        //Create instance for appRepo and ViewModel
        val appRepo = MarvelAppRepository(characterDao)
        val characterViewModelFactory = ViewModelFactory(appRepo)
        val cvm = ViewModelProvider(this,characterViewModelFactory)[CharacterViewModel::class.java]
        //cvm.getCharacterById(2,5,selectedId.toString())

        setContent {
            MarvelCharacterAppTheme {
                //Get context
                val context = LocalContext.current
                //Instance for NavController
                val navController = rememberNavController()
                //Fetch selectedCharacter Info by selectedId by calling function from ViewModel
                cvm.getCharacterById(2,5,selectedId.toString())

                Scaffold(modifier = Modifier.fillMaxSize(),
                    //TopBar to display title
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Character Detail View",
                                color = Color.White)
                        },
                            // Set background color of the TopAppBar
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = Color.Blue // Set background color (purple in this case)
                            ))
                    },
                    //Bottom bar to display NavItem between Detail,Comics and Stories screens
                    bottomBar = {
                        Column {
                            Row(modifier = Modifier.fillMaxHeight(0.15f),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically){
                                MyBottomBar(onNavigate = {path ->
                                    navController.navigate(path)
                                })
                            }
                            //Footer to display copyright message from Composable FooterUI
                            Row(modifier = Modifier.fillMaxHeight(0.09f)) {
                                FooterUI() // This places the footer as a bottomBar
                            }
                        }

                    }

                ) { innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        val currentContext = LocalContext.current

                        //Composable to Navigate between DetailScreen, Comics, Series
                        MyNavGraph(navController,cvm,selectedId,
                            oneComicSelected = {comicId->
                                Log.d("Value","Entered Comic Detail Activity with $comicId")
                                val intent = Intent(currentContext,ComicDetailActivity::class.java)
                                intent.putExtra("comicId",comicId)
                                currentContext.startActivity(intent)
                            })
                    }


                }
            }
        }
    }
}

//Composable to get NavItems to navigate between screens
@Composable
fun MyBottomBar(onNavigate : (String)->Unit  ){
    val navItems = listOf(NavItem.Detail, NavItem.Comics, NavItem.Series)
    var context = LocalContext.current
    var selectedIndex = remember { mutableStateOf(0) }
    //Display NavBar with NavBarItems
    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex.value,
                label = { Text(text = item.title) },
                //Called when Clicking any NavItem
                onClick = {
                    selectedIndex.value = index
                    //Toast.makeText(context,item.title, Toast.LENGTH_SHORT).show()
                    onNavigate(item.path)
                },
                //Icon to display image for each NavItem
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )

        }
    }

}



