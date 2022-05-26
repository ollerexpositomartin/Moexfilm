package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.Cast
import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar los actores de la respuesta a TMDB
 */
data class TMDBResponseCast(
    @SerializedName("cast")
    val cast: List<Cast>,
)
