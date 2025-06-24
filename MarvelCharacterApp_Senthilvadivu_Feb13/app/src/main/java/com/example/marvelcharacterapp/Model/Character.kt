package com.example.marvelcharacterapp.Model


//Data member to collect information from Result
data class Character(
    val id : Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val description : String,
    val thumbnail : String,
    val thumbnailExt : String,
    val comicsAvailable : Int,
    val comicsURL : List<String>,
    val comicsName : List<String>,
    val seriesAvailable : Int,
    val seriesURl : List<String>,
    val seriesName : List<String>,
    val storiesAvailable : Int,
    val storiesURL : List<String>,
    val storiesName : List<String>,
    val storiesType : List<String>,
    val eventsAvailable : Int,
    val eventsURL : List<String>,
    val eventsName : List<String>,
    val urlsType : List<String>,
    val urlsURL : List<String>
    )

