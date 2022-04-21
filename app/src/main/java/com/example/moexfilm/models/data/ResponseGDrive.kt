package com.example.moexfilm.models.data

import com.google.gson.annotations.SerializedName

data class ResponseGDrive(
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("files", alternate = ["drives"]) val listGDriveItems: List<GDriveItem>
)