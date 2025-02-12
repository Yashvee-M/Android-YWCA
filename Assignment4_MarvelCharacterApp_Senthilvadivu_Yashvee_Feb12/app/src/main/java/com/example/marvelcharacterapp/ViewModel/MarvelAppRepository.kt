package com.example.marvelcharacterapp.ViewModel


import com.example.marvelcharacterapp.Model.CharacterApiService
import com.example.marvelcharacterapp.Model.CharacterApiServiceInterface
import com.example.marvelcharacterapp.Room.CharacterDAO
import com.example.marvelcharacterapp.Room.CharacterEntity
import com.example.marvelcharacterapp.data_source.characterDto_source.CharactersDto
import com.example.marvelcharacterapp.data_source.comicsDto_source.ComicsDto
import kotlinx.coroutines.flow.Flow


class MarvelAppRepository(private val characterDao: CharacterDAO) :
    CharacterApiServiceInterface {
    // Live Update from Room DB - Observable - Flow

        //Source of Truth
    var characterApiService =  CharacterApiService()

    override suspend fun getCharactersFromAPI(offset: Int,limit:Int): CharactersDto {
        return characterApiService.getCharactersFromAPI(offset,limit)
    }

    override suspend fun getAllSearchedCharacter(offset: Int,
        limit: Int,
        search: String): CharactersDto {
        return characterApiService.getAllSearchedCharacter(offset,limit,search)
    }


    override suspend fun getCharacterById(offset: Int,
                                          limit: Int,
                                          characterId: String): CharactersDto {
        return characterApiService.getCharacterById(offset,limit,characterId)
    }

    override suspend fun getComicByIdFromApi(offset: Int, limit: Int, comicId: String): ComicsDto {
        return characterApiService.getComicById(offset,limit,comicId)
    }


    suspend fun insertNewCharacterInDB(c: CharacterEntity):Boolean{
        var isAlreadyInDB = false
        var list = getSimilarCharacters(c.name)// check for same city in db
        if (list.size == 0){
            characterDao.addNewCharacterToDB(c)
        }else {
            isAlreadyInDB = true
        }
        return isAlreadyInDB
    }

    suspend fun updateOneCharacter(c: CharacterEntity){
        characterDao.updateOneCharacter(c)
    }
    suspend fun deleteOneCharacterFromDB(c: CharacterEntity){
        characterDao.deleteOneCharacterFromDB(c)
    }

    fun searchForCharacter(t: String): Flow<List<CharacterEntity>>{
        return characterDao.secondFunctoToGetCharacterEqualsTo(t)
    }
    suspend fun getSimilarCharacters(t: String):List<CharacterEntity>{
        return characterDao.getCharacterEqualsTo(t)
    }

    fun getAllCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacterFromDB()
    }


}