package com.example.moexfilm.models.data.mediaObjects

class TvShow : TMDBItem {

    var seasons:HashMap<String,Any> = hashMapOf()

    constructor() : super()

    constructor(
        idDrive: String,
        name: String,
        fileName: String,
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
    )

    override fun toString(): String {
        return "TvShow(seasons=$seasons)"
    }

}