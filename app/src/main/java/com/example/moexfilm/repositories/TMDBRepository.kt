package com.example.moexfilm.repositories

import android.util.Log
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.helpers.RetrofitHelper
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.services.TMDBService
import com.example.moexfilm.util.StringUtil

object TMDBRepository {

    private val TMDB_URL = "https://api.themoviedb.org"
    private val API_KEY = "8be905875a365e0038efdb4a5a19d4fe"

    suspend fun searchMovies(files:MutableList<GDriveItem>, language:String, callback: TMDBCallBack){
        if(files.size > 0)
        for(file in files) {
            val formatTitle = StringUtil.extractTitleAndDate(file.name)
            val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                .searchMovie(
                    API_KEY,
                    formatTitle.name,
                    formatTitle.year,
                    language
                )

            if(response.isSuccessful){
               val result = response.body()
                if(result!!.results.isNotEmpty()) {
                    val movie = result.results[0]
                    movie.idDrive = file.id
                    movie.fileName = file.name
                    callback.onSearchItemCompleted(movie)
                }
            }
        }
        callback.onAllSearchsFinish()
    }
}