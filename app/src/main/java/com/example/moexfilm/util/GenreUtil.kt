package com.example.moexfilm.util

object GenreUtil {
    fun changeGenreIdForName(id:Int):String{
        return when(id){
            28 -> "Acción"
            12 -> "Aventura"
            16 -> "Animación"
            35 -> "Comedia"
            80 -> "Crimen"
            99 -> "Documental"
            18 -> "Drama"
            10751 -> "Familia"
            14 -> "Fantasía"
            36 -> "Historia"
            27 -> "Terror"
            10402 -> "Música"
            9648 -> "Misterio"
            10749 -> "Romance"
            878 -> "Ciencia ficción"
            10770 -> "TV de series"
            53 -> "Thriller"
            10752 -> "Guerra"
            37 -> "Western"
            else -> "No encontrado"
        }
    }
}