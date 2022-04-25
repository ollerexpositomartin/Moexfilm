package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

class Movie : MediaItem {
    var duration: Long = 0
    var timePlayed: Long = 0
    var quality: Long = 0

    constructor()

    constructor(
        idDrive: String,
        name:String,
        fileName: String,
        parent: String,
        id: Int,
        poster_path: String,
        backdrop_path: String,
        genre_ids: List<Int>,
        popularity: Double,
        vote_average: Double,
        overview: String
    ) : super(
        idDrive,
        name,
        fileName,
        parent,
        id,
        poster_path,
        backdrop_path,
        genre_ids,
        popularity,
        vote_average,
        overview
    )

}
