package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable

/**
 * Clase que representa una libreria vacia en la base de datos
 */
open class Library:Serializable{
     var owner: String = ""
     var id: String = ""
     var name: String = ""
     var type: String = ""
     var language: String = ""

    constructor()

    constructor(owner: String,id: String,name: String,type: String, language: String){
        this.owner = owner
        this.id = id
        this.name = name
        this.type = type
        this.language = language
    }
}
