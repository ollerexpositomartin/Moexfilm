package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.mediaObjects.Episode
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.Season
import com.example.moexfilm.models.data.responseObjects.TMDBResponseCast
import com.example.moexfilm.models.data.responseObjects.TMDBResponseMovie
import com.example.moexfilm.models.data.responseObjects.TMDBResponseTvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseMovie>

    @GET("/3/movie/{movie_id}")
    suspend fun searchMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Movie>

    @GET("/3/search/tv")
    suspend fun searchTvShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("first_air_date_year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseTvShow>

    @GET("/3/tv/{tv_id}/season/{season_number}")
    suspend fun searchTvSeason(
        @Path("tv_id") tv_id:Int,
        @Path("season_number") season_number:Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Season>

    @GET("/3/tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun searchTvEpisode(
        @Path("tv_id") tv_id:Int,
        @Path("season_number") season_number:Int,
        @Path("episode_number") episode_number:Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Episode>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<TMDBResponseCast>

}