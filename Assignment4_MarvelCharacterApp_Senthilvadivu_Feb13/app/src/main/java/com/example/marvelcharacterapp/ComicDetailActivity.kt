package com.example.marvelcharacterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.marvelcharacterapp.DetailNavigation.ComicNavItem
import com.example.marvelcharacterapp.DetailNavigation.MyComicNavGraph
import com.example.marvelcharacterapp.Room.CharacterDatabase
import com.example.marvelcharacterapp.View.FooterUI
import com.example.marvelcharacterapp.ViewModel.ComicsViewModel
import com.example.marvelcharacterapp.ViewModel.ComicsViewModelFactory
import com.example.marvelcharacterapp.ViewModel.MarvelAppRepository
import com.example.marvelcharacterapp.ui.theme.MarvelCharacterAppTheme

//Activity to display detailed view of Comic selected with NavBar by fetch the Comic info from MarvelApi
class ComicDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val comicId = intent.getStringExtra("comicId")
        Log.d("Value","Comic Id $comicId")
        //Create instance for Database, Dao
        val database = CharacterDatabase.getDBInstance(this)
        val characterDao = database.getCharacterDao()

        //Create instance for appRepo and ViewModel
        val appRepo = MarvelAppRepository(characterDao)
        val comicsViewModelFactory = ComicsViewModelFactory(appRepo)
        val comicVM = ViewModelProvider(this,comicsViewModelFactory)[ComicsViewModel::class.java]
        //Get Comic detail by selected Comic Id
        if (comicId != null) {
            comicVM.getComicDetailFromAPI(2,5,comicId)
        }


        setContent {
            MarvelCharacterAppTheme {
                val comicNavController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    //TopBar to display title
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Comic Detail View",
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
                            Row(modifier = Modifier.fillMaxHeight(0.15f)){
                                MyComicBottomBar(onNavigate = {path ->
                                    comicNavController.navigate(path)
                                })
                            }
                            //Footer to display copyright message from Composable FooterUI
                            Row(modifier = Modifier.fillMaxHeight(0.09f)) {
                                FooterUI() // This places the footer as a bottomBar
                            }
                        }

                    }
                ) { innerPadding ->
                    Column (Modifier.padding(innerPadding)) {
                        val currentContext = LocalContext.current
                        //Composable to Navigate between DetailScreen, Comics, Series
                        if (comicId != null) {
                            MyComicNavGraph(comicNavController,comicVM,comicId)
                        }
                    }
                }
            }
        }
    }
}

//Composable to get NavItems to navigate between screens
@Composable
fun MyComicBottomBar(onNavigate : (String)->Unit  ){
    val navItems = listOf(ComicNavItem.Detail, ComicNavItem.Creators, ComicNavItem.Characters,ComicNavItem.Images)
    val context = LocalContext.current
    val selectedIndex = remember { mutableStateOf(0) }
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


