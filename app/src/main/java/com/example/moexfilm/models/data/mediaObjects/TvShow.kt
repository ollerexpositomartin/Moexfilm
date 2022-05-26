package com.example.moexfilm.models.data.mediaObjects

import com.example.moexfilm.models.interfaces.Likable
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

/**
 * Clase para almacenar los datos de una serie de televisión
 */
class TvShow : TMDBItem,Serializable,Likable {
    var seasons:HashMap<String,Season> = hashMapOf()
    var like:Boolean = false
    var firebaseType:String = ""

    constructor() : super()

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
        like:Boolean,
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
        released_date,
    ){
        this.like = like
        this.firebaseType = firebaseType
    }


    override fun assingLike(like: Boolean) { this.like = like }

    override fun obtainLike(): Boolean = like

}