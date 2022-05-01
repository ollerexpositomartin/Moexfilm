package com.example.moexfilm.application

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class Prefs(c: Context) {

    private val FILE: String = "SESSION"
    private val UID: String = "uid"
    private val fbUser = FirebaseAuth.getInstance().currentUser

    private val storage: SharedPreferences = c.getSharedPreferences(FILE,0)

    fun saveUid(uid:String){
        storage.edit().putString(UID,uid).apply()
    }

    fun readUid():String{
        return if (fbUser?.uid != null) fbUser.uid else storage.getString(UID,null)!!
    }

    fun cleanSession(){
        storage.edit().clear().apply()
    }

}