package com.example.marvelcharacterapp.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharacterapp.Model.Character
import com.example.marvelcharacterapp.Model.Comic
import com.example.marvelcharacterapp.data_source.comicsDto_source.ComicsDto
import kotlinx.coroutines.launch


class ComicsViewModel(var appRepo : MarvelAppRepository ) : ViewModel() {

    var apiComicsList by mutableStateOf<ComicsDto?>(null)
    var comicsStateList by mutableStateOf<List<Comic>>(emptyList())

    fun getComicDetailFromAPI(offset:Int,limit:Int,comicId:String) {
        viewModelScope.launch {
            apiComicsList = appRepo.getComicByIdFromApi(offset,limit,comicId)
            for (result in apiComicsList!!.data.results){
                comicsStateList += result.toComic()
            }
        }

    }

}