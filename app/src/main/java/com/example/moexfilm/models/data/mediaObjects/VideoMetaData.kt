package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

data class VideoMetaData(
    @SerializedName("width")
    val quality:Long,
    @SerializedName("durationMillis")
    val duration:Long
)
