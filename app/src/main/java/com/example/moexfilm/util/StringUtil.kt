package com.example.moexfilm.util

import android.util.Log
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
        val p = Pattern.compile("(^[A-zÀ-ÿñ:\\-0-9 ]+)(\\([0-9]{4}\\))?")
        val match = p.matcher(title)
        val formatTitle = FormatTitle("","")
        if (match.find()) {
            val titleMovie = match.group(1) ?: ""
            val year = match.group(2)?.replace(Regex("[\\(-\\)]"), "") ?: ""
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

    fun getSeasonNumber(name:String):Int{
        val p = Pattern.compile("[0-9]+")
        val matcher = p.matcher(name)

        if(matcher.find()){
            return matcher.group(0)?.toInt()!!
        }
        return -1
    }

    fun getEpisodeNumber(name:String):Int{
        val p = Pattern.compile("([xXeE])([0-9]+)")
        val matcher = p.matcher(name)

        if(matcher.find()){
            return matcher.group(2)?.toInt()!!
        }
        return -1
    }

    fun dateToYear(date:String):String{
        val p = Pattern.compile("[0-9]{4}")
        val matcher = p.matcher(date)

        if(matcher.find()){
            return matcher.group(0)!!
        }
        return ""
    }

    fun minToHoursAndMinutes(min:Long):String{
        val hours = min/60
        val minutes = min%60
        return "${hours}h ${minutes}m"
    }

}
