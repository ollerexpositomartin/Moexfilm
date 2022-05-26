package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar los metadatos de un video
 */
data class VideoMetaData(
    @SerializedName("width")
    val quality:Long,
    @SerializedName("durationMillis")
    val duration:Long
)
