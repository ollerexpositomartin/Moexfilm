package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.example.moexfilm.R
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.mediaObjects.*
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.callBacks.TMDBCallBack
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.repositories.FirebaseDBRepository
import com.example.moexfilm.repositories.GDriveRepository
import com.example.moexfilm.repositories.TMDBRepository
import kotlinx.coroutines.*
import java.util.stream.Collectors
import kotlin.properties.Delegates

/**
 * Esta clase se ejecuta en segundo plano con un ciclo de vida distinto al de la aplicación y es la encargada de llamar a los metodos para realizar la busquedad de los archivos
 * en GDRIVE, obtener la informacion referente a TMDB y almacenar los resultados en la base de datos
 */
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

    /**
     * Esta clase es devuelta al cliente cuando el servicio se inicia para que poder establecer una conexion con el servicio
     */
    inner class LocalBinder : Binder() {
        fun getService(): ScanLibraryService {
            return this@ScanLibraryService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    /**
     * Este metodo sirve para poder asignarle al servicio un listener y escuchar los cambios en su estado
     * @param serviceListener listener que notifica al cliente cuando el servicio se inicia o se detiene
     */
    fun setScanServiceListener(serviceListener: ServiceListener) {
        this.serviceListener = serviceListener
    }

    /**
     * Este metodo añade la libreria a la lista de librerias en escaneo y se encarga de llamar a los metodos para obtener las subfolders de la libreria y decidir si se debe escanear como peliculas o como series
     * @param library libreria a escanear
     */
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

    /**
     *Este metodo se encargar de llamar a los metodos para obtener las series,temporadas, episodios de la libreria y almacenarlos en la base de datos
     * @param library libreria padre de las series
     * @param tvShows Series a escanear
     */
    suspend fun scanTvShows(library: Library, tvShows: ArrayList<GDriveItem>?) {
        val tvShowsTMDB:MutableList<TvShow> = mutableListOf()

        TMDBRepository.searchTvShows(tvShows, library.language, object : TMDBCallBack {
            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                val tvShow = itemTMDB as TvShow

                tvShow.parentFolder = library.id
                tvShow.parentLibrary = library.id
                tvShow.firebaseType = tvShow::class.java.simpleName

                tvShowsTMDB.add(tvShow)

                FirebaseDBRepository.saveMovieAndTvShowInLibrary(tvShow)
            }
            override fun onAllSearchsFinish() {
               runBlocking(Dispatchers.IO){
                    scanTvSeasons(library, tvShowsTMDB)
                }
            }
        })
    }

    /**
     * Este metodo se encarga de llamar a los metodos para obtener las temporadas de las series buscar su informacion en TMDB y almacenarlos en la base de datos
     * @param library libreria padre de las series
     * @param tvShows Series ya escaneadas
     */
    private suspend fun scanTvSeasons(library: Library, tvShows: MutableList<TvShow>) {
        tvShows.forEach { tvShow ->
            GDriveRepository.getChildItems(queryFormatFolders.format(tvShow.idDrive), object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveItem>?) {
                       response?.forEach { gdriveItem ->
                           val season:Season =  Season()
                           season.idDrive = gdriveItem.idDrive
                           season.fileName = gdriveItem.fileName

                           tvShow.seasons[season.idDrive] = season
                       }
                    }
                    override fun onFailure() {} })
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
                runBlocking(Dispatchers.IO){ scanTvEpisodes(library, tvShows) }
            }
        })
    }

    /**
     * Este metodo se encarga de llamar a los metodos para obtener los episodios de las temporadas, buscar su informacion en TMDB y almacenarlos en la base de datos
     * @param library libreria padre de las series
     * @param tvShows Series ya escaneadas con las temporadas escaneadas
     */
    private suspend fun scanTvEpisodes(library: Library, tvShows:MutableList<TvShow>) {
            tvShows.forEach { tvShow ->
                tvShow.seasons.values.forEach { season ->
                    GDriveRepository.getChildItems(queryFormatFiles.format(season.idDrive), object : GDriveCallBack {
                            override fun onSuccess(response: ArrayList<GDriveItem>?) {
                                response?.forEach { gdriveItem ->
                                    val episode = Episode()
                                    episode.idDrive = gdriveItem.idDrive
                                    episode.fileName = gdriveItem.fileName
                                    tvShow.seasons[season.idDrive]!!.episodes[episode.idDrive] = episode
                                }
                            }
                            override fun onFailure() {} })
                }
            }
        TMDBRepository.searchTvEpisode(tvShows,library.language,object:TMDBCallBack{
            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                val episode = itemTMDB as Episode
                episode.firebaseType = Episode::class.java.simpleName
                FirebaseDBRepository.saveEpisode(episode)
            }
            override fun onAllSearchsFinish() {
                removeLibrary(library)
            }
        })
    }

    /**
     * Este metodo se encarga de llamar a los metodos para obtener las peliculas, buscar su informacion en TMDB y almacenarlos en la base de datos
     * @param library libreria padre de las peliculas
     * @param movieFolders peliculas a escanear
     */
    suspend fun scanMovies(library: Library, movieFolders: ArrayList<GDriveItem>?) {
        val foldersToScan: MutableList<GDriveItem> = movieFolders ?: mutableListOf()
        foldersToScan.add(GDriveItem(library.name, library.id))
        val query: String = foldersToScan.stream().map { item -> queryFormatFiles.format(item.idDrive) }
            .collect(Collectors.joining(" or ")).plus(" and mimeType = 'video/x-matroska'")

        GDriveRepository.getChildItems(query, object : GDriveCallBack {
            override fun onSuccess(response: ArrayList<GDriveItem>?) {
                CoroutineScope(Dispatchers.IO).launch {
                    TMDBRepository.searchMovies(response ?: mutableListOf(), library.language, object : TMDBCallBack {
                            override fun onSearchItemCompleted(itemTMDB: TMDBItem) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val movie = itemTMDB as Movie
                                    movie.parentFolder = library.id
                                    movie.parentLibrary = library.id
                                    movie.firebaseType = Movie::class.java.simpleName
                                    FirebaseDBRepository.saveMovieAndTvShowInLibrary(movie)
                                }
                            }
                            override fun onAllSearchsFinish() {
                                removeLibrary(library)
                            }
                        })
                }
            }
            override fun onFailure() {
                runBlocking(Dispatchers.Main){ Toast.makeText(this@ScanLibraryService, String.format(getString(R.string.scanningLibraries_error), library.name), Toast.LENGTH_LONG).show()}
                removeLibrary(library)
            }
        })
    }

    /**
     * Este metodo se encarga de eliminar la libreria de la lista de liberias en escaneo
     * @param library libreria a eliminar de la lista
     */
    fun removeLibrary(library: Library) {
        currentLibrariesScanning.remove(library)
        currentLibrariesScanning = currentLibrariesScanning
    }
}

