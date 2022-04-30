package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.Cast
import com.google.gson.annotations.SerializedName

data class TMDBResponseCast(
    @SerializedName("cast")
    val cast: List<Cast>,
)
