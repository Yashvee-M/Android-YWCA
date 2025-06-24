package com.example.marvelcharacterapp.data_source.characterDto_source

//Contains data members to capture Character ApiResponse
data class CharactersDto(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)