package com.example.marvelcharacterapp.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface CharacterDAO {

    // to insert new character to database
    @Insert
    suspend fun addNewCharacterToDB(c: CharacterEntity)
    // to delete character from database
    @Delete
    suspend fun deleteOneCharacterFromDB(characterToDelete: CharacterEntity)
    //Update oneCharacter to database
    @Update
    suspend fun updateOneCharacter(cityToUpdate: CharacterEntity)

    // select * from CharacterEntity === get all characters from database
    @Query("select * from CharacterEntity")
    fun getAllCharacterFromDB() : Flow<List<CharacterEntity>>

    // select All character from CharacterEntity where name like given String  "
    @Query("select * from CharacterEntity where character_name LIKE '%' || :text || '%'")
    suspend fun getCharacterEqualsTo(text: String) : List<CharacterEntity>

    // select All character from CharacterEntity where name like given String  "
    @Query("select * from CharacterEntity where character_name LIKE '%' || :text || '%'")
    fun secondFunctoToGetCharacterEqualsTo(text: String) : Flow<List<CharacterEntity>>


}