package com.example.moexfilm.models.data.utilObjects

/*
 * Clase para almacenar el titulo formateado de un elemento Multimedia
 */
class FormatedTitle{
    lateinit var name:String
    lateinit var year:String

    constructor()
    constructor(name:String, year:String){
        this.name = name
        this.year = year
    }
}
