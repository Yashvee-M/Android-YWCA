package com.example.marvelcharacterapp.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

//Class to hold all the required Constants to pass to API
class Constants {
    //Provide the values to query Api
    companion object{
        const val BASE_URL = "https://gateway.marvel.com"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val API_KEY = "Enter your Public key here from Marvel Developer Portal"
        const val PRIVATE_KEY = "Enter your Private key here from Marvel Developer Portal"
        const val offset = "0"
        const val limit = 5

        //Create hash value using current timeStamp and keys using MD5 method
        fun hash() : String {
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1,md.digest(input.toByteArray())).toString(16).padStart(32,'0')
        }
    }
}