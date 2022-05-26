package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

/**
 * Clase para almacenar los tokens devueltos en la autenticación
 */
data class Token(
    @SerializedName("access_token") val accessToken:String?,
    @SerializedName("refresh_token") val refreshToken:String?
)
