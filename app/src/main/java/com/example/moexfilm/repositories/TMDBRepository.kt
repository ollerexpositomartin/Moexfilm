package com.example.moexfilm.repositories

import android.util.Log
import com.example.moexfilm.models.data.mediaObjects.GDriveItem
import com.example.moexfilm.models.data.mediaObjects.Season
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.models.helpers.RetrofitHelper
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.services.TMDBService
import com.example.moexfilm.util.StringUtil

object TMDBRepository {

    private const val TMDB_URL = "https://api.themoviedb.org"
    private const val API_KEY = "8be905875a365e0038efdb4a5a19d4fe"

    suspend fun searchMovies(
        files: MutableList<GDriveItem>,
        language: String,
        callback: TMDBCallBack
    ) {
        if (files.size > 0)
            for (file in files) {
                val formatTitle = StringUtil.extractTitleAndDate(file.name)
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
                        val movie = result.results[0]
                        movie.idDrive = file.id
                        movie.fileName = file.name
                        callback.onSearchItemCompleted(movie)
                    }
                }
            }
        callback.onAllSearchsFinish()
    }


    suspend fun searchTvShows(
        folders: MutableList<GDriveItem>,
        language: String,
        callback: TMDBCallBack
    ) {
        if (folders.size > 0)
            for (folder in folders) {
                val formatTitle = StringUtil.extractTitleAndDate(folder.name)
                val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                    .searchTvShow(API_KEY, formatTitle.name, formatTitle.year, language)

                if (response.isSuccessful) {
                    val result = response.body()
                    if (result!!.results.isNotEmpty()) {
                        val tvShow = result.results[0]
                        tvShow.idDrive = folder.id
                        tvShow.fileName = folder.name
                        callback.onSearchItemCompleted(tvShow)
                    }
                }
            }
        callback.onAllSearchsFinish()
    }

    suspend fun searchTvSeason(tvShow: TvShow, seasons: MutableList<GDriveItem>, language: String, callback: TMDBCallBack) {
        if (seasons.size > 0)
            for (season in seasons) {
                val numberSeason = StringUtil.getSeasonNumber(season.name)
                val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                    .searchTvSeason(
                        tvShow.id,
                        numberSeason,
                        API_KEY,
                        language
                    )

                if (response.isSuccessful) {
                    val seasonTMDB = response.body()!!
                    seasonTMDB.idDrive = season.id
                    seasonTMDB.fileName = season.name
                    callback.onSearchItemCompleted(seasonTMDB)
                }
            }
        callback.onAllSearchsFinish()
    }

    suspend fun searchTvEpisode(tvShow: TvShow, season: Season, episodes: MutableList<GDriveItem>, language: String, callback: TMDBCallBack) {
        if (episodes.size > 0)
            for (episode in episodes) {
                //POR AQUI EL ERROR
                val episodeNumber = StringUtil.getEpisodeNumber(episode.name)
                val response = RetrofitHelper.getRetrofit(TMDB_URL).create(TMDBService::class.java)
                    .searchTvEpisode(
                        tvShow.id,
                        season.season_number,
                        episodeNumber,
                        API_KEY,
                        language
                    )

                if (response.isSuccessful) {
                    val episodeTMDB = response.body()!!
                    episodeTMDB.idDrive = episode.id
                    episodeTMDB.fileName = episode.name
                    callback.onSearchItemCompleted(episodeTMDB)
                }

            }
        callback.onAllSearchsFinish()
    }


}