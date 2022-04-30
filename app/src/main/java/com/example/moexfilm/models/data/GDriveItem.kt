package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


 data class GDriveItem(
    @SerializedName("name") var fileName:String,
    @SerializedName("id") val idDrive:String
):Serializable

