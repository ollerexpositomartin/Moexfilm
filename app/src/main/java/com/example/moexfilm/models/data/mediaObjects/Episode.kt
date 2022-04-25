package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

class Episode {
    var idDrive: String = ""
    @SerializedName("name")
    var name:String = ""
    @SerializedName("overview")
    var overview:String = ""
    @SerializedName("still_path")
    var stillPath:String = ""
    @SerializedName("vote_average")
    var voteAverage:Double = 0.0

    constructor()

    constructor(idDrive: String, name: String, overview: String, stillPath: String, voteAverage: Double) {
        this.idDrive = idDrive
        this.name = name
        this.overview = overview
        this.stillPath = stillPath
        this.voteAverage = voteAverage
    }
}
