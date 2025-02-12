package com.example.marvelcharacterapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class ComicsViewModelFactory(private val appRepo : MarvelAppRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ComicsViewModel::class.java)){
            return ComicsViewModel(appRepo) as T
        }else if (modelClass.isAssignableFrom(ComicsViewModel::class.java)){
            return ComicsViewModel(appRepo) as T
        }
        else {
            throw IllegalArgumentException("Not matching class")
        }
    }
}