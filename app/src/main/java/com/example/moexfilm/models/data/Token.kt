package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token") val accessToken:String?,
    @SerializedName("refresh_token") val refreshToken:String?
)
