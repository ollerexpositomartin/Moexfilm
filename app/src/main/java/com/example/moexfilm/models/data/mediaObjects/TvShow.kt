package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class TvShow : TMDBItem,Serializable {
    var seasons:HashMap<String,Any> = hashMapOf()

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
    )

}