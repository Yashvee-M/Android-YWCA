package com.example.marvelcharacterapp.data_source.characterDto_source

import com.example.marvelcharacterapp.Model.Character

//Member of Data class
data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
){
    //Function to convert Results to Character Model class
    fun toCharacter():Character{
        return Character(
            id=id,
            modified=modified,
            name= name,
            resourceURI=resourceURI,
            description=description,
            thumbnail=thumbnail.path,
            thumbnailExt = thumbnail.extension,
            comicsAvailable = comics.available,
            comicsURL = comics.items.map{
                it.resourceURI
            },
            comicsName = comics.items.map{
                it.name
            },
            seriesAvailable = series.available,
            seriesURl = series.items.map{
                it.resourceURI
            },
            seriesName = series.items.map{
                it.name
            },
            storiesAvailable = stories.available,
            storiesURL = stories.items.map{
                it.resourceURI
            },
            storiesName = stories.items.map{
                it.name
            },
            storiesType = stories.items.map{
                it.type
            },
            eventsAvailable = events.available,
            eventsURL = events.items.map{
                it.resourceURI
            },
            eventsName = events.items.map{
                it.name
            },
            urlsURL = urls.map{
                it.url
            },
            urlsType = urls.map{
                it.type
            }

        )
    }
}