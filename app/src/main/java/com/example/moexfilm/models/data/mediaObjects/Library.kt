package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable

class Library:Serializable {
     var owner: String = ""
     var id: String = ""
     var name: String = ""
     var content:Map<String,Any> = emptyMap()
     var type: String = ""
     var language: String = ""

    constructor()

    constructor(owner: String,id: String,name: String,content:Map<String,Any>,type: String, language: String){
        this.owner = owner
        this.id = id
        this.name = name
        this.content = content
        this.type = type
        this.language = language
    }
}
