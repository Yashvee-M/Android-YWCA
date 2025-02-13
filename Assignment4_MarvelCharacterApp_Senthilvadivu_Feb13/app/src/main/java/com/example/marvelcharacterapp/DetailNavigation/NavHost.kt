package com.example.marvelcharacterapp.DetailNavigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.marvelcharacterapp.View.ComicDetailScreens.ComicCharactersUI
import com.example.marvelcharacterapp.View.ComicDetailScreens.ComicCreatorsUI
import com.example.marvelcharacterapp.View.ComicDetailScreens.ComicDetailUI
import com.example.marvelcharacterapp.View.ComicDetailScreens.ComicImagesUI
import com.example.marvelcharacterapp.View.DetailScreens.CharacterDetailUI
import com.example.marvelcharacterapp.View.DetailScreens.ComicsUI
import com.example.marvelcharacterapp.View.DetailScreens.SeriesUI
import com.example.marvelcharacterapp.ViewModel.CharacterViewModel
import com.example.marvelcharacterapp.ViewModel.ComicsViewModel

//Contains Screens called according to selected NavItem for CharacterDetail Activity
@Composable
fun MyNavGraph(navController: NavHostController, cvm:CharacterViewModel, selectedId:Int, oneComicSelected:(String)->Unit){
    NavHost(navController = navController,
        startDestination = NavItem.Detail.path) {
        //Path to different screen
        composable(route = NavItem.Detail.path) { CharacterDetailUI(
               modifier = Modifier,
               cvm,
               selectedId)
        }
        composable(route = NavItem.Comics.path) { ComicsUI(cvm, oneComicSelected = {comicId->
            oneComicSelected(comicId)
        }) }
        composable(route = NavItem.Series.path) { SeriesUI(cvm)  }

    }
}

//Contains Screens called according to selected NavItem for ComicDetail Activity
@Composable
fun MyComicNavGraph(navController: NavHostController, comicVM: ComicsViewModel,comicId:String){
    NavHost(navController = navController,
        startDestination = ComicNavItem.Detail.path) {
        //Path to different screen
        composable(route = ComicNavItem.Detail.path) { ComicDetailUI(modifier=Modifier,comicVM, comicId ) }
        composable(route = ComicNavItem.Creators.path) { ComicCreatorsUI(modifier = Modifier,comicVM,comicId)  }
        composable(route = ComicNavItem.Characters.path) { ComicCharactersUI(modifier = Modifier,comicVM,comicId) }
        composable(route = ComicNavItem.Images.path) { ComicImagesUI(modifier = Modifier,comicVM,comicId) }

    }
}