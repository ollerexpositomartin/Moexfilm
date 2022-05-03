package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class Season : TMDBItem,Serializable {

    constructor():super()
    @SerializedName("")
    var episodes:HashMap<String,Episode> = hashMapOf()
    @SerializedName("season_number")
    var season_number: Int = 0

    constructor(
        idDrive: String,
        name: String,
        season_number: Int,
        released_date: String?,
        fileName: String,
        episodes:HashMap<String,Episode>,
        parentFolder: String,
        parentLibrary: String,
        id: Int,
        poster_path: String,
        backdrop_path: String,
        genres: List<Genre>,
        popularity: Double,
        vote_average: Double,
        overview: String
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
        this.episodes = episodes
        this.season_number = season_number
    }


}
