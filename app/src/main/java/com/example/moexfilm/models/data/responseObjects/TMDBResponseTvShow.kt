package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.google.gson.annotations.SerializedName

data class TMDBResponseTvShow(
    @SerializedName("results") val results: List<TvShow>
)