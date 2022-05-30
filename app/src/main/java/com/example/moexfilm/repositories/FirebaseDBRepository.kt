package com.example.moexfilm.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.moexfilm.application.Application.Access.REFRESH_TOKEN
import com.example.moexfilm.application.Prefs
import com.example.moexfilm.application.capitalize
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.Token
import com.example.moexfilm.models.data.mediaObjects.*
import com.example.moexfilm.models.data.utilObjects.FirebaseObjectIdentificator
import com.example.moexfilm.models.data.utilObjects.FormatedTitle
import com.example.moexfilm.models.interfaces.Likable
import com.example.moexfilm.models.interfaces.Playable
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.stream.Collectors

/**
 * Clase que contiene los metodos para poder interactuar con la base de datos de Firebase
 */
object FirebaseDBRepository {
    private const val FIREBASE_DB_URL =
        "https://moexfilm-default-rtdb.europe-west1.firebasedatabase.app/"
    private val dataBaseReference: FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DB_URL)
    private var database: DatabaseReference

    init {
        dataBaseReference.setPersistenceEnabled(true)
        database = dataBaseReference.reference
    }

    /**
     * Metodo que crea una libreria en la base de datos
     * @param library libreria a crear
     * @param firebaseDBCallBack callback de la operacion de creación libreria
     */
    fun createLibrary(library: Library, firebaseDBCallBack: FirebaseDBCallBack) {
        database.child("users").child(Prefs.readUid()).child("libraries").child(library.id)
            .setValue(library)
            .addOnSuccessListener {
                firebaseDBCallBack.onSuccess(library as Any)
            }
            .addOnFailureListener {
                firebaseDBCallBack.onFailure()
            }
    }

    /**
     * Metodo que almacena una pelicula o serie en la base de datos
     * @param item TMDBItem a guardar en la libreria
     * @param callback callback de la operacion de guardado del item
     */
    fun saveMovieAndTvShowInLibrary(item: TMDBItem, callback: FirebaseDBCallBack? = null) {
        database.child("users").child(Prefs.readUid()).child("libraries").child(item.parentFolder)
            .child("content").child(item.idDrive).setValue(item).addOnSuccessListener {
                callback?.onSuccess(item)
            }
            .addOnFailureListener {
                callback?.onFailure()
            }
    }

    /**
     * Metodo que almacena una temporada en la base de datos
     * @param season temporada a guardar en la libreria
     * @param callback callback de la operacion de guardado de la temporada
     */
    fun saveSeason(season: Season, callback: FirebaseDBCallBack? = null) {
        database.child("users").child(Prefs.readUid()).child("libraries")
            .child(season.parentLibrary)
            .child("content").child(season.parentFolder).child("seasons").child(season.idDrive)
            .setValue(season).addOnSuccessListener {
                callback?.onSuccess(season)
            }
            .addOnFailureListener {
                callback?.onFailure()
            }
    }

    /**
     * Metodo que almacena un capitulo en la base de datos
     * @param episode episodio a guardar en la libreria
     * @param callback callback de la operacion de guardado del episodio
     */
    fun saveEpisode(episode: Episode) {
        database.child("users").child(Prefs.readUid()).child("libraries")
            .child(episode.parentLibrary)
            .child("content").child(episode.parentTvShow).child("seasons")
            .child(episode.parentFolder).child("episodes").child(episode.idDrive).setValue(episode)
    }

    /**
     * Almacena el refreshToken de la cuenta del usuario que crear la libreria en la base de datos
     * @param account cuenta del usuario
     */
    fun saveAccountRefreshToken(account: Account) {
        database.child("users").child(Prefs.readUid()).child("accounts")
            .child(account.id).setValue(account.refreshToken)
    }

    /**
     * Metodo que obtiene las librerias del usuario y les hace un post a lista pasada como parametro
     * @param libraries librerias del usuario
     */
    fun setListenerLibraries(libraries: MutableLiveData<List<Library>>) {
        database.child("users").child(Prefs.readUid()).child("libraries")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Library>()

                    for (dataSnapShot in snapshot.children) {
                        list.add(dataSnapShot.getValue(Library::class.java)!!)
                    }
                    libraries.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    /**
     * Metodo que obtiene el contenido de una libreria y añade sus items a la lista pasada como parametro
     * @param library libreria a obtener el contenido
     * @param items lista donde se añadiran los items
     */
    fun setListenerItemLibrary(library: Library, items: MutableLiveData<List<TMDBItem>>) {
        database.child("users").child(Prefs.readUid()).child("libraries").child(library.id)
            .child("content")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<TMDBItem>()

                    if (library.type == "Peliculas") {
                        for (dataSnapShot in snapshot.children) {
                            list.add(dataSnapShot.getValue(Movie::class.java)!!)
                        }
                    }

                    if (library.type == "Series") {
                        for (dataSnapShot in snapshot.children) {
                            list.add(dataSnapShot.getValue(TvShow::class.java)!!)
                        }
                    }

                    items.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    /**
     * Metodo que obtiene 4 elementos aleatorios de las librerias de peliculas del usuario y lo añade a la lista pasada como parametro
     * @param randomItems lista donde se añadiran los items
     */
    fun getRandomContent(randomItems: MutableLiveData<MutableList<TMDBItem>>) {
        database.child("users").child(Prefs.readUid()).child("libraries")
            .orderByChild("type")
            .equalTo("Peliculas").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listMovies = mutableListOf<TMDBItem>()
                    val randomLocalItems = mutableListOf<TMDBItem>()

                    for (dataSnapShot in snapshot.children) {
                        val data = dataSnapShot.getValue(LibraryMovies::class.java)!!
                        listMovies.addAll(data.content.values.stream().collect(Collectors.toList()))
                    }

                    val size = 4
                    val s = HashSet<Int>(size)
                        if (listMovies.size > size) {
                            while (s.size < size) {
                                s.add((0 until listMovies.size).random())
                            }

                            s.stream().forEach { random ->
                                randomLocalItems.add(listMovies.get(random))
                            }
                            randomItems.postValue(randomLocalItems)
                        }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    /**
     * Metodo que obtiene los 10 elementos mas populares de las librerias de peliculas del usuario y lo añade a la lista pasada como parametro
     * @param randomItems lista donde se añadiran los items
     */
    fun getMostPopularMovies(popularMovies: MutableLiveData<MutableList<TMDBItem>>) {
        database.child("users").child(Prefs.readUid()).child("libraries")
            .orderByChild("type")
            .equalTo("Peliculas").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listMovies: MutableList<TMDBItem> = mutableListOf()
                    val moviesNoRepeat:HashMap<String,Movie> = hashMapOf()
                    for (dataSnapShot in snapshot.children) {
                        val data = dataSnapShot.getValue(LibraryMovies::class.java)!!
                        val movies = data.content.values.stream().collect(Collectors.toList())

                        for(movie in movies){
                            moviesNoRepeat[movie.name] = movie
                        }
                    }
                    listMovies.addAll(moviesNoRepeat.values)
                    listMovies = listMovies.stream().sorted{movie1,movie2 -> (movie2.popularity!!*1000 - movie1.popularity!!*1000).toInt() }.collect(Collectors.toList())

                    try {
                        listMovies = listMovies.subList(0,9)
                    }catch (_:Exception){
                    }

                    popularMovies.postValue(listMovies)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    /**
     * Metodo que almacena en la base de datos una pelicula o episodio a medio reproducir
     * @param media el item a almacenar
     */
    fun saveMediaInProgress(media: TMDBItem) {
        database.child("users").child(Prefs.readUid()).child("inProgress").child(media.idDrive).setValue(media)
    }

    /**
     * Metodo que obtiene todos los elementos a medio reproducir y los añade a la lista pasada como parametro
     * @param inProgress lista donde se añadiran los items
     */
    fun getMediaInProgress(inProgress: MutableLiveData<MutableList<TMDBItem>>) {
        database.child("users").child(Prefs.readUid()).child("inProgress")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listMovies = mutableListOf<TMDBItem>()
                    for (dataSnapShot in snapshot.children) {
                        val data:FirebaseObjectIdentificator = dataSnapShot.getValue(FirebaseObjectIdentificator::class.java)!!

                        val realData:TMDBItem = if(data.firebaseType == Movie::class.java.simpleName){
                            dataSnapShot.getValue(Movie::class.java)!!
                        }else{
                            dataSnapShot.getValue(Episode::class.java)!!
                        }
                        listMovies.add(realData)
                    }
                    inProgress.postValue(listMovies)
                }
                override fun onCancelled(error: DatabaseError) {} })
    }

    /**
     * Metodo que elimina un elemento a medio reproducir de la base de datos
     * @param media elemento a eliminar
     */
    fun removeMediaInProgress(media: TMDBItem) {
        database.child("users").child(Prefs.readUid()).child("inProgress").child(media.idDrive).removeValue()
    }

    /**
     * Metodo que obtiene los elementos likeados por el usuario y los añade a la lista pasada como parametro
     * @param likes lista donde se añadiran los items
     */
    fun getMediaLikes(likes: MutableLiveData<List<TMDBItem>>) {
        database.child("users").child(Prefs.readUid()).child("likes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               val listMedia = mutableListOf<TMDBItem>()

                for(dataSnapShot in snapshot.children){
                    val data:FirebaseObjectIdentificator = dataSnapShot.getValue(FirebaseObjectIdentificator::class.java)!!

                    if(data.firebaseType == Movie::class.java.simpleName){
                        val movie:Movie = dataSnapShot.getValue(Movie::class.java)!!
                        listMedia.add(movie)
                    }

                    if(data.firebaseType == TvShow::class.java.simpleName){
                        val tvShow:TvShow = dataSnapShot.getValue(TvShow::class.java)!!
                        listMedia.add(tvShow)
                    }

                }
                likes.postValue(listMedia)
            }

            override fun onCancelled(error: DatabaseError) {} })
    }

    /**
     * Metodo que añade un elemento likeado por el usuario a la base de datos y en caso de que este likeado lo elimina
     * @param media elemento a añadir
     */
    fun likeMedia(media: TMDBItem) {
        val likable:Likable = media as Likable
        database.child("users").child(Prefs.readUid()).child("libraries").child(media.parentLibrary).child("content").child(media.idDrive).child("like").setValue(likable.obtainLike())

        if(likable.obtainLike()) {
            database.child("users").child(Prefs.readUid()).child("likes").child(media.idDrive)
                .setValue(media)
            return
        }
        database.child("users").child(Prefs.readUid()).child("likes").child(media.idDrive).removeValue()
    }

    /**
     * Metodo que busca un elemento en la base de datos usando su nombre
     * @param query nombre del elemento a buscar
     * @param searchedsItems lista donde se añadiran los elementos encontrados
     */
    fun searchTMDBItems(query:String,searchedsItems:MutableLiveData<List<TMDBItem>>){
        database.child("users").child(Prefs.readUid()).child("libraries")
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listSearchedsItems = mutableListOf<TMDBItem>()

                    for(library in snapshot.children) {
                        Log.d("LIBRARY", library.key.toString())

                        library.child("content").ref.orderByChild("name").startAt(query).endAt(query+"\uf8ff")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                for (dataSnapshot in snapshot.children) {
                                    val firebaseIdentificator = dataSnapshot.getValue(FirebaseObjectIdentificator::class.java)!!

                                    if (firebaseIdentificator.firebaseType == Movie::class.java.simpleName) {
                                        val movie = dataSnapshot.getValue(Movie::class.java)!!
                                        listSearchedsItems.add(movie)
                                    }

                                    if (firebaseIdentificator.firebaseType == TvShow::class.java.simpleName) {
                                        val tvShow: TvShow = dataSnapshot.getValue(TvShow::class.java)!!
                                        listSearchedsItems.add(tvShow)
                                    }
                                }
                                searchedsItems.postValue(listSearchedsItems)
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }
                }
                override fun onCancelled(error: DatabaseError) {} })
    }

    /**
     * Metodo que obtiene el refresh token almacenado en la base de datos
     * @param accountId id de la cuenta de google
     * @param callBack callback que se ejecutara al obtener el refresh token
     */
    fun getRefreshToken(accountId:String,callBack: TokenCallBack){
        Log.d("ACCOUNT_ID",accountId)
        database.child("users").child(Prefs.readUid()).child("accounts").child(accountId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val refreshToken:String = snapshot.getValue(String::class.java)!!
                REFRESH_TOKEN = refreshToken
                callBack.onSucess()
            }
            override fun onCancelled(error: DatabaseError) { callBack.onFailure() }
        })
    }

    /**
     * Metodo que obtiene el account id de una libreria almacenada en la base de datos
     * @param libraryId id de la libreria
     * @param callback callback que se ejecutara al obtener el account id
     */
    fun getAccountId(libraryId:String,callback:FirebaseDBCallBack){
        database.child("users").child(Prefs.readUid()).child("libraries").child(libraryId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val library = snapshot.getValue(Library::class.java)!!
                callback.onSuccess(library.owner)
            }
            override fun onCancelled(error: DatabaseError) {
                callback.onFailure()
            }
        })
    }



}
