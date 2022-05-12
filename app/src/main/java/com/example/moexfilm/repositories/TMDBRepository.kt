package com.example.moexfilm.repositories

import android.util.Log
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.mediaObjects.*
import com.example.moexfilm.models.helpers.RetrofitHelper
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.services.TMDBService
import com.example.moexfilm.util.MediaUtil
import com.example.moexfilm.util.StringUtil

object TMDBRepository {

    private const val TMDB_URL = "https://api.themoviedb.org"
    private const val API_KEY = "8be905875a365e0038efdb4a5a19d4fe"

    suspend fun searchMovies(files: MutableList<GDriveItem>, language: String, callback: TMDBCallBack) {
        if (files.size > 0)
            for (file in files) {
                val formatTitle = StringUtil.extractTitleAndDate(file.fileName)
                val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                    .searchMovie(
                        API_KEY,
                        formatTitle.name,
                        formatTitle.year,
                        language
                    )

                if (response.isSuccessful) {
                    val result = response.body()
                    if (result!!.results.isNotEmpty()) {
                        val tempMovie = result.results[0]
                        searchMovieDetails(tempMovie, language, object : TMDBCallBack {
                            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                                val movie = itemTMDB as Movie
                                movie.idDrive = file.idDrive
                                movie.fileName = file.fileName
                                movie.release_date = StringUtil.dateToYear(movie.release_date?:"")

                                callback.onSearchItemCompleted(movie)
                            }
                            override fun onAllSearchsFinish() {} })
                    }
                }
            }
        callback.onAllSearchsFinish()
    }


    suspend fun searchTvShows(folders: MutableList<GDriveItem>?, language: String, callback: TMDBCallBack) {
        if (folders != null) {
            if (folders.size > 0)
                for (folder in folders) {
                    val formatTitle = StringUtil.extractTitleAndDate(folder.fileName)
                    val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                        .searchTvShow(API_KEY, formatTitle.name, formatTitle.year, language)

                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result!!.results.isNotEmpty()) {
                            val tvShow = result.results[0]
                            tvShow.idDrive = folder.idDrive
                            tvShow.fileName = folder.fileName
                            callback.onSearchItemCompleted(tvShow)
                        }
                    }
                }
        }
        callback.onAllSearchsFinish()
    }

    suspend fun searchTvSeason(tvShows:MutableList<TvShow>, language: String, callback: TMDBCallBack) {
        tvShows.forEach { tvShow ->
            tvShow.seasons.values.forEach { season ->
                val seasonNumber = StringUtil.getSeasonNumber(season.fileName)
                val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                    .searchTvSeason(tvShow.id,seasonNumber,API_KEY,language)

                if (response.isSuccessful) {
                    val seasonTMDB = response.body()!!
                    seasonTMDB.idDrive = season.idDrive
                    seasonTMDB.fileName = season.fileName
                    seasonTMDB.parentLibrary  = tvShow.parentLibrary
                    seasonTMDB.parentFolder = tvShow.idDrive
                    callback.onSearchItemCompleted(seasonTMDB)
                    }
                }
            }
        callback.onAllSearchsFinish()
        }

    suspend fun searchTvEpisode(tvShows:MutableList<TvShow>,language: String, callback: TMDBCallBack) {
        tvShows.forEach { tvShow ->
            tvShow.seasons.values.forEach { season ->
                season.episodes.values.forEach { episode ->
                    val episodeNumber = StringUtil.getEpisodeNumber(episode.fileName)
                    val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                        .searchTvEpisode(
                            tvShow.id,
                            season.season_number,
                            episodeNumber,
                            API_KEY,
                            language
                        )
                    if(response.isSuccessful){
                        val episodeTMDB = response.body()!!
                        episodeTMDB.tvShowName = tvShow.name
                        episodeTMDB.seasonPosterPath = season.poster_path.toString()
                        episodeTMDB.idDrive = episode.idDrive
                        episodeTMDB.fileName = episode.fileName
                        episodeTMDB.parentLibrary  = tvShow.parentLibrary
                        episodeTMDB.parentFolder = season.idDrive
                        episodeTMDB.parentTvShow = tvShow.idDrive
                        callback.onSearchItemCompleted(episodeTMDB)
                    }
                }
            }
        }
        callback.onAllSearchsFinish()
    }

    private suspend fun searchMovieDetails(movie: Movie, language: String, callback: TMDBCallBack) {
        val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
            .searchMovieDetails(movie.id, API_KEY, language)
        if (response.isSuccessful) {
            val result = response.body()!!

            movie.duration = MediaUtil.minutesToMs(result.duration)
            movie.genres = result.genres
            callback.onSearchItemCompleted(movie)
            }
        }

    suspend fun getMovieCast(movie: Movie,onSuccess:((List<Cast>) -> Unit)) {
        val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java).getMovieCast(movie.id, API_KEY)
        if (response.isSuccessful) {
            val result = response.body()!!
            onSuccess(result.cast)
            }
        }
    }









