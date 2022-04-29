package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName
import java.util.*

class Episode : TMDBItem {

    @SerializedName("still_path")
    var stillPath: String = ""
    @SerializedName("episode_number")
    var episode_number:Int = 0
    var parentTvShow: String = ""


    constructor():super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
        episode_number:Int,
        parentFolder: String,
        parentLibrary: String,
        released_date: String?,
        id: Int,
        poster_path: String,
        stillPath:String,
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
        this.stillPath = stillPath
        this.episode_number = episode_number
    }

}
