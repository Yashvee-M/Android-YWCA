package com.example.marvelcharacterapp.data_source.characterDto_source

//member of CharacterDto
data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)