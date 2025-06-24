package com.example.marvelcharacterapp.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity to reflect the database
@Entity
data class CharacterEntity(@PrimaryKey val id : Int,
                           @ColumnInfo("character_name")val name : String,
                           @ColumnInfo("character_description")val description : String,
                           @ColumnInfo("character_thumbnail")val thumbnail : String,
                           @ColumnInfo("character_thumbnailExt")val thumbnailExt : String,
                           @ColumnInfo("character_comics")val comics : String)

