package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.responseObjects.TMDBResponseMovie
import com.example.moexfilm.models.data.responseObjects.TMDBResponseTvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseMovie>

    @GET("/3/search/tv")
    suspend fun searchTvShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseTvShow>
}