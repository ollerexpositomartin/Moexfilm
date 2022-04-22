package com.example.moexfilm.models.data

import java.io.Serializable

data class ScanItem(
    val id: String,
    val type: String,
    val language: String,
    val subFolders:MutableList<GDriveItem>?
):Serializable