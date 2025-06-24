package com.example.marvelcharacterapp.Networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Object class to get Query from MarvelApiInterface

object RetrofitClass {
    //BaseURL for making API call
    private val CharacterBASE_URL = "https://gateway.marvel.com"

    //Retrofit object to fetch information from API and convert it into model object
    var CharacterRetrofitObject =
        Retrofit.Builder().
        addConverterFactory(GsonConverterFactory.create()).
        baseUrl(CharacterBASE_URL).
        build()

    val marvelApi = CharacterRetrofitObject.create(MarvelApiInterface::class.java)


}