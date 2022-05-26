package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.mediaObjects.VideoMetaData
import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar los metadatos de la respuesta a GDAPI
 */
data class VideoMetadataResponse(
    @SerializedName("videoMediaMetadata")
    val videoMetadata:VideoMetaData
)
