package com.example.marvelcharacterapp.ViewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharacterapp.Model.Character
import com.example.marvelcharacterapp.Room.CharacterEntity
import com.example.marvelcharacterapp.data_source.characterDto_source.CharactersDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CharacterViewModel(var appRepo : MarvelAppRepository ) : ViewModel() {

    var apiCharacterList by mutableStateOf<CharactersDto?>(null)
    var apiSearchedCharacterList by mutableStateOf<CharactersDto?>(null)
    var characterStateList by mutableStateOf<List<Character>>(emptyList())
    var apiCharacterIdList by mutableStateOf<CharactersDto?>(null)
    var characterIdStateList by mutableStateOf<List<Character>>(emptyList())



    var found by mutableStateOf<Boolean>(false)

    // AppRepository needs no parameters
    // If I can create App Repo in VM.
    // var appRepo : AppRepository = AppRepository()




    fun getAllCharactersFromAPI(offset:Int,limit:Int) {
        viewModelScope.launch {
            apiCharacterList = appRepo.getCharactersFromAPI(offset,limit)
            for (result in apiCharacterList!!.data.results){
                characterStateList += result.toCharacter()
            }
        }

    }

    fun getAllSearchedCharacter(offset:Int,limit:Int,search:String) {
        viewModelScope.launch {
            apiSearchedCharacterList = appRepo.getAllSearchedCharacter(offset,limit,search)
            for (result in apiSearchedCharacterList!!.data.results){
                characterStateList += result.toCharacter()
            }
        }

    }

    fun getCharacterById(offset:Int,limit:Int,characterId:String) {
        viewModelScope.launch {
            apiCharacterIdList = appRepo.getCharacterById(offset,limit,characterId)
            for (result in apiCharacterIdList!!.data.results){
                characterIdStateList += result.toCharacter()
            }
        }

    }

    fun setListEmpty(){
        characterStateList = emptyList()
    }


    // database Functions
    fun setdbList(){
        //databaseListOfFavCharacters = emptyList()
    }

    fun insertNewCharacterInDB(c: CharacterEntity):Boolean {
        var found = false
        viewModelScope.launch {
            found = appRepo.insertNewCharacterInDB(c)// late true
            appRepo.insertNewCharacterInDB(c)// late true
            //databaseListOfFavCharacters = databaseListOfFavCharacters + c
        }
        return  found// false
    }

    fun updateOneCharacter(c: CharacterEntity){
        viewModelScope.launch {
            appRepo.updateOneCharacter(c)

        }
    }

    fun deleteOneCharacterFromDB(c: CharacterEntity) {
        viewModelScope.launch {
            appRepo.deleteOneCharacterFromDB(c)
            //  databaseListOfFavCities = getAllCities()
        }
    }

    fun searchForCharacter(t: String):Flow<List<CharacterEntity>> {
        return  appRepo.searchForCharacter(t)
    }

    fun getAllCharactersFromDB(): Flow<List<CharacterEntity>> {
        return  appRepo.getAllCharacters()
    }


}