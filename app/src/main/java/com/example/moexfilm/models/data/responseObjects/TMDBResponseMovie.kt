package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.Movie
import com.google.gson.annotations.SerializedName

data class TMDBResponseMovie(
    @SerializedName("results") val results: List<Movie>
)