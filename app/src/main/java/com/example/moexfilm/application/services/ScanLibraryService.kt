package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.models.data.mediaObjects.GDriveItem
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.repositories.FirebaseDBRepository
import com.example.moexfilm.repositories.GDriveRepository
import com.example.moexfilm.repositories.TMDBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder
import kotlin.properties.Delegates


class ScanLibraryService : Service() {
    private lateinit var serviceListener: ServiceListener
    private var currentLibrariesScanning: MutableList<Library> by Delegates.observable(mutableListOf()) { _, _, newValue ->
        runBlocking(Dispatchers.Main) {
            serviceListener.isRunning(newValue)
        }
    }
    private val queryFormatFolders = "'%s' in parents and mimeType = 'application/vnd.google-apps.folder'"
    private val queryFormatFiles = "parents in '%s'"

    inner class LocalBinder : Binder() {
        fun getService(): ScanLibraryService {
            return this@ScanLibraryService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    private val mBinder = LocalBinder()

    fun setScanServiceListener(serviceListener: ServiceListener) {
        this.serviceListener = serviceListener
    }

    fun startScan(library: Library) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                currentLibrariesScanning.add(library)
                currentLibrariesScanning = currentLibrariesScanning

                GDriveRepository.getChildItems(String.format(queryFormatFolders, library.id), object : GDriveCallBack {
                        override fun onSuccess(response: ArrayList<GDriveItem>?) {
                            CoroutineScope(Dispatchers.IO).launch {
                                if (library.type == this@ScanLibraryService.getString(R.string.movies_text))
                                    scanMovies(library, response)
                                if(library.type == this@ScanLibraryService.getString(R.string.tvShows_text))
                                    scanTvShows(library,response)
                            }
                        }
                        override fun onFailure() {
                            Toast.makeText(this@ScanLibraryService,String.format(getString(R.string.scanningLibraries_error), library.name),Toast.LENGTH_LONG).show()
                            removeLibrary(library)
                        }
                    })
            }
        }catch (e:Exception){
            Log.d("ERROR------>",e.toString())
        }
    }

    private fun scanTvShows(library: Library, tvShows: ArrayList<GDriveItem>?) {

    }

    suspend fun scanMovies(library: Library, response: ArrayList<GDriveItem>?) {
        val foldersToScan: MutableList<GDriveItem> = response ?: mutableListOf()
        val query: StringBuilder = StringBuilder()
        var files: List<GDriveItem> = emptyList()

        //AÑADIMOS LA CARPETA ROOT
        foldersToScan.add(GDriveItem(library.name, library.id))

        //CREA LA QUERY CON TODAS LAS CARPETAS ENCONTRADAS ANTERIORMENTE
        for (i in foldersToScan.indices) {
            query.append(queryFormatFiles.format(foldersToScan[i].id))

            if (foldersToScan.size > 1 && i != foldersToScan.size - 1)
                query.append(" or ")
        }
        query.append("and mimeType = 'video/x-matroska'")
        //
        GDriveRepository.getChildItems(query.toString(), object : GDriveCallBack { override fun onSuccess(response: ArrayList<GDriveItem>?) {
                    CoroutineScope(Dispatchers.IO).launch {
                        TMDBRepository.searchMovies(response?:mutableListOf(), library.language, object : TMDBCallBack {
                                override fun onSearchItemCompleted(movie: Movie) {
                                    movie.parent = library.id
                                    FirebaseDBRepository.saveMovieInLibrary(movie)
                                }
                                override fun onAllSearchsFinish() {
                                    removeLibrary(library)
                                }
                            })
                    }
                }
                override fun onFailure() {
                    Toast.makeText(this@ScanLibraryService,String.format(getString(R.string.scanningLibraries_error),library.name),Toast.LENGTH_LONG).show()
                    removeLibrary(library)
                }
            })
    }

    fun removeLibrary(library: Library) {
        currentLibrariesScanning.remove(library)
        currentLibrariesScanning = currentLibrariesScanning
    }
}

