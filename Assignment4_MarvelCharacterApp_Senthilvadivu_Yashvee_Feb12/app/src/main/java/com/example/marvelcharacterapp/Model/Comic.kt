package com.example.marvelcharacterapp.Model

import com.example.marvelcharacterapp.data_source.comicsDto_source.Thumbnail


//Data member to collect information from Result(ComicsDto)
data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val modified: String,
    //val isbn: String,
    //val upc: String,
    val diamondCode: String,
    //val ean: String,
    //val issn: String,
    //val format: String,
    //val pageCount: Int,
    val thumbnail: String,
    val urlsType : List<String>,
    val urlsURL : List<String>,
    val seriesName: String,
    val creatorsName: List<String>,
    val creatorsRole : List<String>,
    val charactersName: List<String>,
    val images: List<String>,
    //val issueNumber: Int,
    //val resourceURI: String,
    //val textObjects: List<TextObject>,
    //val variantDescription: String
)
