package com.example.marvelcharacterapp.Networking

import com.example.marvelcharacterapp.data_source.characterDto_source.CharactersDto
import com.example.marvelcharacterapp.data_source.comicsDto_source.ComicsDto
import com.example.marvelcharacterapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApiInterface {

    //Query to get Characters from baseURL
    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey")apikey:String=Constants.API_KEY,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
        @Query("offset")offset:String,
        @Query("limit")limit:String
    ): CharactersDto

    //Query to get Characters from baseURL, Characters begin with String
    @GET("/v1/public/characters")
    suspend fun getAllSearchedCharacters(
        @Query("apikey")apikey:String=Constants.API_KEY,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
        @Query("offset")offset:String,
        @Query("limit")limit:String,
        @Query("nameStartsWith")search:String
    ): CharactersDto


    //Query to get Character Info from BaseURL by passing CharacterId
    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId")characterId:String,
        @Query("apikey")apikey:String=Constants.API_KEY,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
        @Query("offset")offset:String,
        @Query("limit")limit:String
    ): CharactersDto

    //Query to get Comics Info from BaseURL by passing ComicId
    @GET("/v1/public/comics/{comicId}")
    suspend fun getComicById(
        @Path("comicId")comicId:String,
        @Query("apikey")apikey:String=Constants.API_KEY,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
        @Query("offset")offset:String,
        @Query("limit")limit:String
    ): ComicsDto

}