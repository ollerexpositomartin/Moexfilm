package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

class Movie {
    lateinit var idDrive: String
    var duration: Int = 0
    var timePlayed: Int = 0

    @SerializedName("title")
    lateinit var name: String

    @SerializedName("genre_ids")
    lateinit var genre_ids: List<Int>

    @SerializedName("overview")
    lateinit var overview: String

    @SerializedName("poster_path")
    lateinit var poster_path: String

    @SerializedName("backdrop_path")
    lateinit var backdrop_path: String

    @SerializedName("popularity")
    var popularity: Double = 0.0

    @SerializedName("vote_average")
    var vote_average: Double = 0.0

    constructor()
    constructor(
        idDrive: String,
        name: String,
        duration: Int,
        timePlayed: Int,
        genre_ids: List<Int>,
        overview: String,
        poster_path: String,
        backdrop_path: String,
        popularity:Double,
        vote_average:Double
    ){
        this.idDrive = idDrive
        this.name = name
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
