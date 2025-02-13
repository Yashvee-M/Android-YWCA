package com.example.marvelcharacterapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Abstract class to create database from CharacterEntity
@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun getCharacterDao():CharacterDAO
    // the code to init the database
    // done in background thread

    companion object {
        private var databaseInstance : CharacterDatabase? = null
        // race condition == happened when multiple
        // threads trying to access same resources
        fun getDBInstance(context : Context): CharacterDatabase {
            var inst = databaseInstance
            synchronized(this) {// no race condition
                if (inst == null) {
                    inst = Room.databaseBuilder(
                        context,
                        CharacterDatabase::class.java,
                        "charactersDB"
                    ).build()
                }
                databaseInstance = inst
            }
            return inst!!
        }

    }


}