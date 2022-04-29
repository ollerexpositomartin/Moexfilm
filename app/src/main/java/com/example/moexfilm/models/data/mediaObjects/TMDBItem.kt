package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class TMDBItem: Serializable {
    var idDrive: String = ""
    var fileName: String = ""
    var parentFolder: String = ""
    var parentLibrary:String = ""

    @SerializedName("title", alternate = ["name"])
    var name:String = ""

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("poster_path")
    var poster_path: String? = null

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null

    @SerializedName("genres")
    var genres: List<Genre> = emptyList()

    @SerializedName("popularity")
    var popularity: Double? = null

    @SerializedName("release_date")
    var release_date: String? = null

    @SerializedName("vote_average")
    var vote_average: Double? = null

    @SerializedName("overview")
    var overview: String? = null

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
       genres: List<Genre>,
       popularity: Double,
       vote_average: Double,
       overview: String,
       release_date: String?
   ){
        this.idDrive = idDrive
        this.name = name
        this.fileName = fileName
        this.parentFolder = parentFolder
        this.parentLibrary = parentLibrary
        this.id = id
        this.poster_path = poster_path
        this.backdrop_path = backdrop_path
        this.genres = genres
        this.popularity = popularity
        this.release_date = release_date
        this.vote_average = vote_average
        this.overview = overview
    }


}

