package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

class Movie {
    var idDrive: String = ""
     var parent:String = ""
    var duration: Long = 0
    var timePlayed: Long = 0
    var quality:Long = 0

    @SerializedName("title")
     var name: String = ""
    var fileName:String = ""
    @SerializedName("genre_ids")
    var genre_ids: List<Int> = emptyList()
    @SerializedName("overview")
    var overview: String = ""
    @SerializedName("poster_path")
    var poster_path: String = ""
    @SerializedName("backdrop_path")
    var backdrop_path: String = ""
    @SerializedName("popularity")
    var popularity: Double = 0.0
    @SerializedName("vote_average")
    var vote_average: Double = 0.0

    constructor()
    constructor(
        idDrive: String,
        parent:String,
        name: String,
        fileName:String,
        duration: Long,
        timePlayed: Long,
        genre_ids: List<Int>,
        overview: String,
        poster_path: String,
        backdrop_path: String,
        popularity:Double,
        vote_average:Double
    ){
        this.idDrive = idDrive
        this.name = name
        this.fileName = fileName
        this.parent = parent
        this.duration = duration
        this.timePlayed = timePlayed
        this.genre_ids = genre_ids
        this.overview = overview
        this.poster_path = poster_path
        this.backdrop_path = backdrop_path
        this.popularity = popularity
        this.vote_average = vote_average
    }
}
