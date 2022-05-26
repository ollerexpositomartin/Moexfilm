package com.example.moexfilm.models.data.mediaObjects

import com.example.moexfilm.models.interfaces.Likable
import com.example.moexfilm.models.interfaces.Playable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Clase para almacenar los datos de una pelicula
 */
class Movie : TMDBItem,Serializable,Playable,Likable {
     @SerializedName("runtime")
     var duration: Long = 0
     var playedTime: Long = 0
     var quality: Long = 0
    var firebaseType:String = ""
    var like:Boolean = false

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
        quality:Long,
        firebaseType:String,
        like:Boolean
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
        this.firebaseType = firebaseType
        this.like = like
    }

    /**
     * Devuelve la duracion del episodio
     * @return Duracion del episodio en milisegundos
     */
    override fun duration(): Long  = duration

    /**
     * Devuelve el tiempo que se ha reproducido del episodio
     * @return Tiempo que se ha reproducido del episodio en milisegundos
     */
    override fun playedTime(): Long = playedTime

    /**
     * Asigna si el elemento esta o no como favorito
     */
    override fun assingLike(like: Boolean) { this.like = like }

    /**
     * Devuelve si el elemento esta o no como favorito
     * @return devuelve si el elemento esta o no como favorito
     */
    override fun obtainLike(): Boolean = like

    override fun toString(): String {
        return  super.toString()+ "Movie(duration=$duration, playedTime=$playedTime, quality=$quality)"
    }


}
