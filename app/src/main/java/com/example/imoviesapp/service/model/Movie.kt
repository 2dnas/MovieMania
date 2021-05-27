package com.example.imoviesapp.service.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val title : String?,
    val description : String?,
    val movieData : MovieHelper?

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<MovieHelper>(MovieHelper.javaClass.classLoader)
    )

    data class MovieHelper (
        val poster : String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(poster)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MovieHelper> {
            override fun createFromParcel(parcel: Parcel): MovieHelper {
                return MovieHelper(parcel)
            }

            override fun newArray(size: Int): Array<MovieHelper?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(movieData?.poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}