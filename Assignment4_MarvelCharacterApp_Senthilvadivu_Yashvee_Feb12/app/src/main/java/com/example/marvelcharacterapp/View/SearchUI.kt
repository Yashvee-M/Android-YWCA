package com.example.marvelcharacterapp.View

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

//SearchBar composable to enter characters used in MainActivity and FavoriteList Activity
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarUI(hint: (String),searchForCharacter : (String)->Unit){
    //To hold the letters in searchText within SearchBar
    var searchText by remember { mutableStateOf("") }
    //flag to get state of searchBar
    var activeStateOfSearchBar by remember { mutableStateOf(true) }
    //Create searchBar
    SearchBar(modifier = Modifier.fillMaxWidth(),
        //searchQuery
        query = searchText,
        //handle searchQuery change event when searchText is 3 character otherwise empty
        onQueryChange = {
            searchText = it
            if (searchText.length > 2){
                searchForCharacter(searchText)
            }
            else{
                searchForCharacter("")
            }

        },
        //handle event while searchBar active
        onSearch = {
            activeStateOfSearchBar = true
        },
        //set searchBar active or not
        active = activeStateOfSearchBar,
        //handle onActive event change, set activeState to current flag
        onActiveChange = {
            activeStateOfSearchBar =it
        },
        //Place holder in SearchBar
        placeholder = { Text(hint) }) {
        Text(searchText)
    }



}