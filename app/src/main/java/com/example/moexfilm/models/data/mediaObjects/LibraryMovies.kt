package com.example.moexfilm.models.data.mediaObjects
import java.io.Serializable

/**
 * Clase que representa una libreria de peliculas en la base de datos
 */
class LibraryMovies:Library,Serializable {
    var content:Map<String,Movie> = emptyMap()

    constructor():super()

    constructor(owner: String, id: String, name:String, content:Map<String,Movie>, type: String, language: String):super(owner, id, name, type, language){
        this.content = content
    }

}