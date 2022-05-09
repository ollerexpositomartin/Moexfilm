package com.example.moexfilm.models.data.mediaObjects

import java.io.Serializable

class LibraryTvShows:Library,Serializable {
    var content:Map<String,TvShow> = emptyMap()

    constructor():super()

    constructor(owner: String,id: String,name:String,content:Map<String,TvShow>,type: String, language: String):super(owner, id, name, type, language){
        this.content = content
    }

}