package com.example.moexfilm.models.data

import java.io.Serializable

data class SubGDriveItem(
    val children:MutableList<GDriveItem>
):Serializable