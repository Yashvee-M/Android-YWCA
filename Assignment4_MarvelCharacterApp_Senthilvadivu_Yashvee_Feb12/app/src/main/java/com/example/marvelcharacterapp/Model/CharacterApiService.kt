package com.example.marvelcharacterapp.Model

import com.example.marvelcharacterapp.Networking.RetrofitClass
import com.example.marvelcharacterapp.data_source.characterDto_source.CharactersDto
import com.example.marvelcharacterapp.data_source.comicsDto_source.ComicsDto
import com.example.marvelcharacterapp.util.Constants

//interface which implements the AppService methods to AppRepo
interface CharacterApiServiceInterface {
    suspend fun getCharactersFromAPI(offset:Int,limit:Int) : CharactersDto
    suspend fun getAllSearchedCharacter(offset:Int,limit:Int,search:String) : CharactersDto
    suspend fun getCharacterById(offset:Int,limit:Int,characterId:String) : CharactersDto
    suspend fun getComicByIdFromApi(offset:Int,limit:Int,comicId:String) : ComicsDto
}

//Service class to access the ApiInterface functions
class CharacterApiService {
    // connecting API to fetch characters data
    private val apiService = RetrofitClass.marvelApi

    //Connect to Api and return all Characters to AppRepo
    suspend fun getCharactersFromAPI(offset:Int,limit:Int) : CharactersDto {
        return  apiService.getAllCharacters(
            Constants.API_KEY,
            Constants.timeStamp,
            Constants.hash(),
            offset.toString(),
            limit.toString()
            )
    }

    //Connect to Api and return All Character Starts with 3 Characters
    suspend fun getAllSearchedCharacter(offset:Int,limit:Int,search:String) : CharactersDto {
        return  apiService.getAllSearchedCharacters(
            Constants.API_KEY,
            Constants.timeStamp,
            Constants.hash(),
            offset.toString(),
            limit.toString(),
            search
        )
    }

    //Connect to Api and return Character Details with CharacterId
    suspend fun getCharacterById(offset:Int,limit:Int,characterId:String) : CharactersDto {
        return  apiService.getCharacterById(
            characterId,
            Constants.API_KEY,
            Constants.timeStamp,
            Constants.hash(),
            offset.toString(),
            limit.toString()
        )
    }

    //Connect to Api and return Comic Details with ComicId
    suspend fun getComicById(offset:Int,limit:Int,comicById:String) : ComicsDto {
        return  apiService.getComicById(
            comicById,
            Constants.API_KEY,
            Constants.timeStamp,
            Constants.hash(),
            offset.toString(),
            limit.toString()
        )
    }


}