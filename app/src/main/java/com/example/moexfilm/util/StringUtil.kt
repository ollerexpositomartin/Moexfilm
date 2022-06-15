package com.example.moexfilm.util

import android.util.Log
import com.example.moexfilm.models.data.mediaObjects.Genre
import com.example.moexfilm.models.data.utilObjects.FormatedTitle
import com.example.moexfilm.models.data.mediaObjects.Library
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

object StringUtil {
    /**
     * Metodo que valida que el formato de email es valido
     * @param email email a validar
     * @return devuelve true si el formato es valido, false si no lo es
     */
    fun validateEmailFormat(email: String): Boolean {
        val p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val match = p.matcher(email)
        if (match.find())
            return true
        return false
    }

    /**
     * Metodo que de un titulo separa el nombre de la fecha
     * @param title titulo del contenido
     * @return devuelve un objeto FormatedTitle con el nombre y la fecha
     */
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

    /**
     * Metodo que formatea una lista de librerias escaneando a un string
     * @param libraryItemList lista de librerias
     * @return devuelve el nombre de las librerias separadas por comas
     */
     fun scanItemListToText(libraryItemList:MutableList<Library>):String{
        return libraryItemList.stream().map { library -> library.name }.collect(Collectors.joining(", "))
    }

    /**
     * Metodo que obtiene el numero de una temporada de un titulo
     * @param name titulo
     * @return devuelve el numero de la temporada
     */
    fun getSeasonNumber(name:String):Int{
        val p = Pattern.compile("[0-9]+")
        val matcher = p.matcher(name)

        if(matcher.find()){
            return matcher.group(0)?.toInt()!!
        }
        return -1
    }

    /**
     * Metodo que obtiene el numero de un episodio de un titulo
     * @param name titulo
     * @return devuelve el numero de episodio
     */
    fun getEpisodeNumber(name:String):Int{
        val p = Pattern.compile("([xXeE])([0-9]+)")
        val matcher = p.matcher(name)

        if(matcher.find()){
            return matcher.group(2)?.toInt()!!
        }
        return -1
    }

    /**
     * Metodo que obtiene de un String con la fecha el año
     * @param date fecha
     * @return devuelve el año
     */
    fun dateToYear(date:String):String{
        val p = Pattern.compile("[0-9]{4}")
        val matcher = p.matcher(date)

        if(matcher.find()){
            return matcher.group(0)!!
        }
        return ""
    }

    /**
     * Metodo que formatea una lista de generos a un string
     * @param genres lista de generos
     * @return devuelve el nombre de los generos separados por comas
     */
    fun genresToString(genres: List<Genre>): String {
        return genres.map {genre -> genre.name }.joinToString(separator = ", ")
    }

}
