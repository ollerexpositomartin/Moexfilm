package com.example.moexfilm.models.data

class FormatTitle{
    lateinit var name:String
    lateinit var year:String

    constructor()
    constructor(name:String, year:String){
        this.name = name
        this.year = year
    }
}
