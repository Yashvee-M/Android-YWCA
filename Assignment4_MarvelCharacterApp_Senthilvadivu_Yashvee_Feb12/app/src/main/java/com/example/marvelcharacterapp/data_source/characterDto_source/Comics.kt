package com.example.marvelcharacterapp.data_source.characterDto_source

//Member of Result class
data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)