package com.example.moexfilm.models.data.mediaObjects

import com.example.moexfilm.models.interfaces.Playable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie : TMDBItem,Serializable,Playable {
     @SerializedName("runtime")
     var duration: Long = 0
     var playedTime: Long = 0
     var quality: Long = 0

    constructor():super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
        parentFolder: String,
        parentLibrary: String,
        released_date: String?,
        id: Int,
        poster_path: String,
        backdrop_path: String,
        genres: List<Genre>,
        popularity: Double,
        vote_average: Double,
        overview: String,
        duration:Long,
        playedTime:Long,
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
        genres,
        popularity,
        vote_average,
        overview,
        released_date
    ){
        this.duration = duration
        this.playedTime = playedTime
        this.quality = quality
    }

    override fun duration(): Long  = duration
    override fun playedTime(): Long = playedTime


}
