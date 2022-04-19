package com.example.moexfilm.util

import com.example.moexfilm.application.Application.Access.prefs
import com.google.firebase.auth.FirebaseAuth

object FirebaseUtil {
    private val fbUser = FirebaseAuth.getInstance().currentUser

    fun getUid(): String {
        return if (fbUser?.uid != null) fbUser.uid else prefs.readUid()!!
    }

}