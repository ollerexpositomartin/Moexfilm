package com.example.moexfilm.application

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class Prefs(c: Context) {
    private val fbUser = FirebaseAuth.getInstance().currentUser

    fun readUid():String{
        return fbUser?.uid ?:""
    }

    fun closeSession(){
        FirebaseAuth.getInstance().signOut()
    }

}