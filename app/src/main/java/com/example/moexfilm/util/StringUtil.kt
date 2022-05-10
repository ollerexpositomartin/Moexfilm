package com.example.moexfilm.util

import android.util.Log
import com.example.moexfilm.models.data.mediaObjects.Genre
import com.example.moexfilm.models.data.utilObjects.FormatedTitle
import com.example.moexfilm.models.data.mediaObjects.Library
import java.util.regex.Pattern
import java.util.stream.Collectors

object StringUtil {
    fun validateEmailFormat(email: String): Boolean {
        val p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val match = p.matcher(email)
        if (match.find())
            return true
        return false
    }

    fun extractTitleAndDate(title: String): FormatedTitle {
        val p = Pattern.compile("(^[A-zÀ-ÿñ:,\\-0-9 ]+)(\\([0-9]{4}\\))?")
        val match = p.matcher(title)
        val formatTitle = FormatedTitle("","")
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
        return libraryItemList.stream().map { library -> library.name }.collect(Collectors.joining(", "))
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



    fun genresToString(genres: List<Genre>): String {
        return genres.map {genre -> genre.name }.joinToString(separator = ", ")
    }



}
