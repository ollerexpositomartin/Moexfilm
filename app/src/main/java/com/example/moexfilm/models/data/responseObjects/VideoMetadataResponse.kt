package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.VideoMetaData
import com.google.gson.annotations.SerializedName

data class VideoMetadataResponse(
    @SerializedName("videoMediaMetadata")
    val videoMetadata:VideoMetaData
)
