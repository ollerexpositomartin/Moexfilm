package com.example.moexfilm.application

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

/**
 * Esta Clase es la contiene los metodos para controlar la sesion actual del cliente
 */

class Prefs{
    private val fbUser = FirebaseAuth.getInstance().currentUser

    /**
     * Este metodo devuelve si el uid si hay un usuario logueado si no un String vacio
     * @return Devuelve el uid del usuario que esta logueado si no hay ningun usuario logueado devuelve un string vacio
     */
    fun readUid():String{
        return fbUser?.uid ?:""
    }

    /**
     * Cierra la sesion de cliente actual
     */
    fun closeSession(){
        FirebaseAuth.getInstance().signOut()
    }

}