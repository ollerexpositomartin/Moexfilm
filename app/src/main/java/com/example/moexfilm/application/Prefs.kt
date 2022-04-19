package com.example.moexfilm.application

import android.content.Context
import android.content.SharedPreferences

class Prefs(c: Context) {

    private val FILE: String = "SESSION"
    private var UID: String = "uid"

    private val storage: SharedPreferences = c.getSharedPreferences(FILE,0)

    public fun saveUid(uid:String){
        storage.edit().putString(UID,uid).apply()
    }

    public fun readUid():String?{
        return storage.getString(UID,null)
    }

    public fun cleanSession(){
        storage.edit().clear().apply()
    }

}