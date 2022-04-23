package com.example.moexfilm.models.data

class Library {
    lateinit var owner: String
    lateinit var id: String
    lateinit var name: String
    lateinit var type: String
    lateinit var language: String

    constructor()

    constructor(owner: String,id: String,name: String, type: String, language: String){
        this.owner = owner
        this.id = id
        this.name = name
        this.type = type
        this.language = language
    }
}
