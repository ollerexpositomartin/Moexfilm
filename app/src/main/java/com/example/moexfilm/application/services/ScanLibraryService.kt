package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.moexfilm.R
import com.example.moexfilm.models.data.mediaObjects.*
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.repositories.FirebaseDBRepository
import com.example.moexfilm.repositories.GDriveRepository
import com.example.moexfilm.repositories.TMDBRepository
import kotlinx.coroutines.*
import java.util.stream.Collectors
import kotlin.properties.Delegates


class ScanLibraryService : Service() {
    private lateinit var serviceListener: ServiceListener
    private val mBinder = LocalBinder()
    private val queryFormatFolders = "'%s' in parents and mimeType = 'application/vnd.google-apps.folder'"
    private val queryFormatFiles = "parents in '%s'"
    private var currentLibrariesScanning: MutableList<Library> by Delegates.observable(mutableListOf()) { _, _, newValue ->
        runBlocking(Dispatchers.Main) {
            serviceListener.isRunning(newValue)
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): ScanLibraryService {
            return this@ScanLibraryService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    fun setScanServiceListener(serviceListener: ServiceListener) {
        this.serviceListener = serviceListener
    }

    fun startScan(library: Library) {
            CoroutineScope(Dispatchers.IO).launch {
                currentLibrariesScanning.add(library)
                currentLibrariesScanning = currentLibrariesScanning

                GDriveRepository.getChildItems(queryFormatFolders.format(library.id), object : GDriveCallBack {
                        override fun onSuccess(response: ArrayList<GDriveItem>?) {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (library.type == this@ScanLibraryService.getString(R.string.movies_text))
                                    scanMovies(library, response)

                                if (library.type == this@ScanLibraryService.getString(R.string.tvShows_text))
                                    scanTvShows(library, response)
                            }
                        }
                        override fun onFailure() {
                            Toast.makeText(this@ScanLibraryService, String.format(getString(R.string.scanningLibraries_error), library.name), Toast.LENGTH_LONG).show()
                            removeLibrary(library)
                        }
                    })
            }
    }

    suspend fun scanTvShows(library: Library, tvShows: ArrayList<GDriveItem>?) {
        val tvShowsTMDB:MutableList<TvShow> = mutableListOf<TvShow>()

        TMDBRepository.searchTvShows(tvShows, library.language, object : TMDBCallBack {
            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                itemTMDB.parentFolder = library.id
                itemTMDB.parentLibrary = library.id
                tvShowsTMDB.add(itemTMDB as TvShow)
                FirebaseDBRepository.saveMovieAndTvShowInLibrary(itemTMDB)
            }
            override fun onAllSearchsFinish() {
                CoroutineScope(Dispatchers.IO).launch {
                    scanTvSeasons(library, tvShowsTMDB)
                }
            }
        })
    }

    private suspend fun scanTvSeasons(library: Library, tvShows: MutableList<TvShow>) {
        tvShows.forEach { tvShow ->
            GDriveRepository.getChildItems(queryFormatFolders.format(tvShow.idDrive), object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveItem>?) {
                       response?.forEach {
                           tvShow.seasons[it.idDrive] = it
                       }
                    }
                    override fun onFailure() {}
                })
        }
        TMDBRepository.searchTvSeason(tvShows, library.language, object : TMDBCallBack {
            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                val season = itemTMDB as Season
                tvShows.forEach{ tvShow ->
                    if(tvShow.seasons.containsKey(season.idDrive))
                        tvShow.seasons[season.idDrive] = season
                }
              FirebaseDBRepository.saveSeason(season)
            }
            override fun onAllSearchsFinish() {
                //Implementar
                //scanTvEpisodes()
            }
        })

    }


    private suspend fun scanTvEpisodes(library: Library, tvShow: TvShow, season: Season) {
        try {
            GDriveRepository.getChildItems(queryFormatFiles.format(season.idDrive),
                object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveItem>?) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val episodes = response as MutableList<GDriveItem>
                            TMDBRepository.searchTvEpisode(tvShow, season, episodes, library.language, object : TMDBCallBack {
                                    override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                                        val episode = itemTMDB as Episode
                                        episode.parentFolder = season.idDrive
                                        episode.parentLibrary = library.id
                                        episode.parentTvShow = tvShow.idDrive
                                        FirebaseDBRepository.saveEpisode(episode)
                                    }
                                    override fun onAllSearchsFinish() {
                                    }
                                })
                        }
                    }
                    override fun onFailure() {
                    }
                })
        } catch (e: Exception) {
            Log.d("Error", e.printStackTrace().toString())
        }
    }

    suspend fun scanMovies(library: Library, response: ArrayList<GDriveItem>?) {
        val foldersToScan: MutableList<GDriveItem> = response ?: mutableListOf()
        val query: String = foldersToScan.stream().map { item -> queryFormatFiles.format(item.idDrive) }
            .collect(Collectors.joining(" or ")).plus(" and mimeType = 'video/x-matroska'")

        GDriveRepository.getChildItems(query.toString(), object : GDriveCallBack {
            override fun onSuccess(response: ArrayList<GDriveItem>?) {
                CoroutineScope(Dispatchers.IO).launch {
                    TMDBRepository.searchMovies(response ?: mutableListOf(), library.language, object : TMDBCallBack {
                            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                                itemTMDB.parentFolder = library.id
                                itemTMDB.parentLibrary = library.id
                                FirebaseDBRepository.saveMovieAndTvShowInLibrary(itemTMDB)
                            }
                            override fun onAllSearchsFinish() {
                                removeLibrary(library)
                            }
                        })
                }
            }
            override fun onFailure() {
                Toast.makeText(this@ScanLibraryService, String.format(getString(R.string.scanningLibraries_error), library.name), Toast.LENGTH_LONG).show()
                removeLibrary(library)
            }
        })
    }

    fun removeLibrary(library: Library) {
        currentLibrariesScanning.remove(library)
        currentLibrariesScanning = currentLibrariesScanning
    }
}

