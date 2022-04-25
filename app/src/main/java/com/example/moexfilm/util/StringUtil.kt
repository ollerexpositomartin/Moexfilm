package com.example.moexfilm.util

import com.example.moexfilm.models.data.utilObjects.FormatTitle
import com.example.moexfilm.models.data.mediaObjects.Library
import java.lang.StringBuilder
import java.util.regex.Pattern

object StringUtil {
    fun validateEmailFormat(email: String): Boolean {
        val p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val match = p.matcher(email)
        if (match.find())
            return true
        return false
    }

    fun extractTitleAndDate(title: String): FormatTitle {
        val p = Pattern.compile("(^[A-z0-9 ]+)(\\([0-9]{4}\\))?")
        val match = p.matcher(title)
        val formatTitle = FormatTitle("","")
        if (match.find()) {
            val titleMovie = match.group(1) ?: ""
            val year = match.group(2)?.replace(Regex("[\\\\(-\\\\)]"), "") ?: ""
            if (titleMovie.isNotEmpty()) {
                formatTitle.name = titleMovie
                if (year.isNotEmpty())
                    formatTitle.year = year

                return formatTitle
            }
        }
        formatTitle.name = title
        return formatTitle
    }

     fun scanItemListToText(libraryItemList:MutableList<Library>):String{
        val text: StringBuilder = StringBuilder()
        for(i in libraryItemList.indices){
            if(i != libraryItemList.size-1)
                text.append(libraryItemList[i].name).append(", ")
            else
                text.append(libraryItemList[i].name)
        }
        return text.toString()
    }

}
