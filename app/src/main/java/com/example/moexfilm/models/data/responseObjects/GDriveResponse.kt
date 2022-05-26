package com.example.moexfilm.models.data.responseObjects

import com.example.moexfilm.models.data.GDriveItem
import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar la respuesta de la petición de la API de Google Drive
 */
data class GDriveResponse(
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("files", alternate = ["drives"]) val listGDriveItems: List<GDriveItem>
)