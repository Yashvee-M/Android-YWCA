package com.example.assignment3_quicknotesapp_yashvee_jan10

import android.os.Parcel
import android.os.Parcelable

data class Note(val title: String, val content: String) : Parcelable {

    // Constructor to create the object from a Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",  // Handle null by providing a default empty string
        parcel.readString() ?: ""
    )

    // Writing the object to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0 // No special content description
    }

    // Companion object for Parcelable.Creator
    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}