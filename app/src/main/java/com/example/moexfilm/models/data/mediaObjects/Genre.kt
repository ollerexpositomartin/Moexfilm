package com.example.moexfilm.models.data.mediaObjects

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Genre:Serializable {
    @SerializedName("name")
    var name: String = ""

    constructor()
    constructor(name: String) {
        this.name = name
    }
}
