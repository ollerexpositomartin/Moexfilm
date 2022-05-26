package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Clase que representa un item de Google Drive
 */
 data class GDriveItem(
    @SerializedName("name") var fileName:String,
    @SerializedName("id") val idDrive:String
):Serializable

