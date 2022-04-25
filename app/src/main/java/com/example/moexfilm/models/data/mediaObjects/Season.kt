package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

class Season {
    var idDrive:String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("poster_path")
    var poster: String = ""
    @SerializedName("episodes")
    var episodes: List<Episode> = emptyList()

    constructor()

    constructor(idDrive: String, name: String, poster: String, episodes: List<Episode>) {
        this.idDrive = idDrive
        this.name = name
        this.poster = poster
        this.episodes = episodes
    }


}
