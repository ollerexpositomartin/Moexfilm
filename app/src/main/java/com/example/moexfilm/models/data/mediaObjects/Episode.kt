package com.example.moexfilm.models.data.mediaObjects

import com.example.moexfilm.models.interfaces.Playable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Episode : TMDBItem,Serializable,Playable {

    @SerializedName("still_path")
    var stillPath: String = ""
    @SerializedName("episode_number")
    var episode_number:Int = -1
    @SerializedName("season_number")
    var season_number:Int = -1
    var tvShowName:String = ""
    var seasonPosterPath:String = ""
    var parentTvShow: String = ""
    var duration:Long = 0
    var playedTime:Long = 0
    var firebaseType:String = ""


    constructor():super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
        tvShowName:String,
        episode_number:Int,
        season_number:Int,
        parentFolder: String,
        parentLibrary: String,
        released_date: String?,
        id: Int,
        poster_path: String,
        stillPath:String,
        seasonPosterPath:String,
        backdrop_path: String,
        genres: List<Genre>,
        popularity: Double,
        vote_average: Double,
        overview: String,
        duration:Long,
        playedTime:Long,
        firebaseType:String
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
        this.tvShowName = tvShowName
        this.stillPath = stillPath
        this.seasonPosterPath = seasonPosterPath
        this.episode_number = episode_number
        this.season_number = season_number
        this.duration = duration
        this.playedTime = playedTime
        this.firebaseType = firebaseType
    }

    override fun duration(): Long = duration

    override fun playedTime(): Long = playedTime
    override fun toString(): String {
        return "Episode(stillPath='$stillPath', episode_number=$episode_number, season_number=$season_number, tvShowName='$tvShowName', seasonPosterPath='$seasonPosterPath', parentTvShow='$parentTvShow', duration=$duration, playedTime=$playedTime)"
    }


}
