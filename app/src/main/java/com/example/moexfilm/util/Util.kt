package com.example.moexfilm.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object Util {
    fun validateEmailFormat(email:String):Boolean{
        val p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val match = p.matcher(email)
        if(match.find())
            return true
        return false
    }
}

/*
@Override
public boolean dispatchTouchEvent(MotionEvent ev){
  return true;//consume
}
 */