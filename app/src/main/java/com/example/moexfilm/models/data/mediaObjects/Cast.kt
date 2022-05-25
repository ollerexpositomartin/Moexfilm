package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar los actores de los elementos Multimedia
 */
data class Cast(
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character:String,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for_department")
    val department: String
)
