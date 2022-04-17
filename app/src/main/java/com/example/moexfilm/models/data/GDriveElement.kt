package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

data class GDriveElement(
    @SerializedName("name") val name:String,
    @SerializedName("id") val id:String
)
