package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar las Series devueltas de la respuesta a TMDB
 */
data class TMDBResponseTvShow(
    @SerializedName("results") val results: List<TvShow>
)