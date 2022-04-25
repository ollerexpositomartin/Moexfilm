package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable

class Library:Serializable {
    lateinit var owner: String
    lateinit var id: String
    lateinit var name: String
    lateinit var content:Map<String,Any>
    lateinit var type: String
    lateinit var language: String

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
