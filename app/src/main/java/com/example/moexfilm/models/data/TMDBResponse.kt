package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

data class TMDBResponse(
    @SerializedName("results") val results: List<Movie>
)