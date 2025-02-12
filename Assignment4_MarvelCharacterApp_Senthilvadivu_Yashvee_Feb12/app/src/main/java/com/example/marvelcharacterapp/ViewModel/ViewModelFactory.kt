package com.example.marvelcharacterapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras


class ViewModelFactory(private val appRepo : MarvelAppRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)){
            return CharacterViewModel(appRepo) as T
        }else if (modelClass.isAssignableFrom(CharacterViewModel::class.java)){
            return CharacterViewModel(appRepo) as T
        }
        else {
            throw IllegalArgumentException("Not matching class")
        }
    }
}