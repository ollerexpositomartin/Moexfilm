package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Movie : TMDBItem,Serializable {
     @SerializedName("runtime")
     var duration: Long = 0
     var timePlayed: Long = 0
     var quality: Long = 0

    constructor():super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
        parentFolder: String,
        parentLibrary: String,
        released_date: String?,
        id: Int,
        poster_path: String,
        backdrop_path: String,
        genres: List<Genre>,
        popularity: Double,
        vote_average: Double,
        overview: String,
        duration:Long,
        timePlayed:Long,
        quality:Long
    ) : super(
        idDrive,
        name,
        fileName,
        parentFolder,
        parentLibrary,
        id,
        poster_path,
        backdrop_path,
        genres,
        popularity,
        vote_average,
        overview,
        released_date
    ){
        this.duration = duration
        this.timePlayed = timePlayed
        this.quality = quality
    }



}
