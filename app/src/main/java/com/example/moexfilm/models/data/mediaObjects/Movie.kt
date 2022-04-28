package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable

class Movie : TMDBItem,Serializable {
    private var duration: Long = 0
    private var timePlayed: Long = 0
    private var quality: Long = 0

    constructor():super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
        parentFolder: String,
        parentLibrary: String,
        id: Int,
        poster_path: String,
        backdrop_path: String,
        genre_ids: List<Int>,
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
        genre_ids,
        popularity,
        vote_average,
        overview
    ){
        this.duration = duration
        this.timePlayed = timePlayed
        this.quality = quality
    }

}
