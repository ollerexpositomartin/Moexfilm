package com.example.moexfilm.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

object StringUtil {
    fun validateEmailFormat(email:String):Boolean{
        val p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val match = p.matcher(email)
        if(match.find())
            return true
        return false
    }
}
