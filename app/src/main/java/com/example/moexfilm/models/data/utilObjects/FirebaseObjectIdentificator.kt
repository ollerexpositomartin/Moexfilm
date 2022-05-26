package com.example.moexfilm.models.data.utilObjects

/**
 * Clase para identificar un objeto traido de Firebase
 */
class FirebaseObjectIdentificator {

    var firebaseType:String = ""

    constructor(){}

    constructor(type:String){
        this.firebaseType = type
    }


}