package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.Movie
import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar las peliculas devueltas de la respuesta a TMDB
 */
data class TMDBResponseMovie(
    @SerializedName("results") val results: List<Movie>
)