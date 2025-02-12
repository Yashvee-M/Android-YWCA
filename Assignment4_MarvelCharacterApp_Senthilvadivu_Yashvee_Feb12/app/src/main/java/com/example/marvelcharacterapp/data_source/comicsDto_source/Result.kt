package com.example.marvelcharacterapp.data_source.comicsDto_source
import com.example.marvelcharacterapp.Model.Comic

//Member of ComicsDto class
data class Result(
    val characters: Characters,
    val creators: Creators,
    val thumbnail:Thumbnail,
    val description: String,
    val diamondCode: String,
    val ean: String,
    val format: String,
    val id: Int,
    val images: List<Image>,
    val isbn: String,
    val issn: String,
    val issueNumber: Int,
    val modified: String,
    val pageCount: Int,
    val resourceURI: String,
    val series: Series,
    val textObjects: List<TextObject>,
    val title: String,
    val upc: String,
    val urls: List<Url>,
    val variantDescription: String
){
    //Function to convert Results to Comic Model class
    fun toComic(): Comic {
        return Comic(
            id = id,
        title =  title,
        description = description,
        modified = modified,
        //val isbn: String,
        //val upc: String,
        diamondCode = diamondCode,
        //val ean: String,
        //val issn: String,
        //val format: String,
        //val pageCount: Int,
        thumbnail = thumbnail.path+"."+thumbnail.extension,
        urlsType = urls.map{
            it.type
        },
        urlsURL = urls.map{
                it.url
        },
        seriesName = series.name,
        creatorsName = creators.items.map {
            it.name
        },
        creatorsRole = creators.items.map {
            it.role
        },

        charactersName = characters.items.map{
            it.name
        },
        images = images.map{
            it.path+"."+it.extension
        } ,
        //val issueNumber: Int,
        //val resourceURI: String,
        //val textObjects: List<TextObject>,
        //val variantDescription: String
        )
    }
}