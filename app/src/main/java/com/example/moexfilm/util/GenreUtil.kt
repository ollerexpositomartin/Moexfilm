package com.example.moexfilm.util

import com.example.moexfilm.models.data.mediaObjects.Genre

object GenreUtil {
   fun genresToString(genres: List<Genre>): String {
      return genres.map {genre -> genre.name }.joinToString(separator = ", ")
   }
}