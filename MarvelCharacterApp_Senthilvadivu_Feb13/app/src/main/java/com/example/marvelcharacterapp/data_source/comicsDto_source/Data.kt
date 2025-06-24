package com.example.marvelcharacterapp.data_source.comicsDto_source

//Member of ComicDto class
data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)