package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

class Season : TMDBItem {

    constructor():super()
    @SerializedName("")
    var episodes:HashMap<String,Any> = hashMapOf()
    @SerializedName("season_number")
    var season_number: Int = 0

    constructor(
        idDrive: String,
        name: String,
        season_number: Int,
        fileName: String,
        episodes:HashMap<String,Any>,
        parentFolder: String,
        parentLibrary: String,
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
        this.episodes = episodes
        this.season_number = season_number
    }


}
