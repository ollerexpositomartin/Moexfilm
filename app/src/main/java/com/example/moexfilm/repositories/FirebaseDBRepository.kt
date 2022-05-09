package com.example.moexfilm.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moexfilm.application.Application.Access.prefs
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.data.mediaObjects.*
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.google.firebase.database.*
import java.util.stream.Collectors
import kotlin.random.Random

object FirebaseDBRepository {
    private const val FIREBASE_DB_URL =
        "https://moexfilm-default-rtdb.europe-west1.firebasedatabase.app/"
    private val dataBaseReference: FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DB_URL)
    private var database: DatabaseReference

    init {
        dataBaseReference.setPersistenceEnabled(true)
        database = dataBaseReference.reference
    }

    fun createLibrary(library: Library, firebaseDBCallBack: FirebaseDBCallBack) {
        database.child("users").child(prefs.readUid()).child("libraries").child(library.id)
            .setValue(library)
            .addOnSuccessListener {
                firebaseDBCallBack.onSuccess(library as Any)
            }
            .addOnFailureListener {
                firebaseDBCallBack.onFailure()
            }
    }

    fun saveMovieAndTvShowInLibrary(item: TMDBItem, callback: FirebaseDBCallBack? = null) {
        database.child("users").child(prefs.readUid()).child("libraries").child(item.parentFolder)
            .child("content").child(item.idDrive).setValue(item).addOnSuccessListener {
                callback?.onSuccess(item)
            }
            .addOnFailureListener {
                callback?.onFailure()
            }
    }

    fun saveSeason(season: Season, callback: FirebaseDBCallBack? = null) {
        database.child("users").child(prefs.readUid()).child("libraries")
            .child(season.parentLibrary)
            .child("content").child(season.parentFolder).child("seasons").child(season.idDrive)
            .setValue(season).addOnSuccessListener {
                callback?.onSuccess(season)
            }
            .addOnFailureListener {
                callback?.onFailure()
            }
    }

    fun saveEpisode(episode: Episode, callback: FirebaseDBCallBack? = null) {
        database.child("users").child(prefs.readUid()).child("libraries")
            .child(episode.parentLibrary)
            .child("content").child(episode.parentTvShow).child("seasons")
            .child(episode.parentFolder).child("episodes").child(episode.idDrive).setValue(episode)
    }


    fun saveAccountRefreshToken(account: Account) {
        database.child("users").child(prefs.readUid()).child("accounts")
            .child(account.id).setValue(account.refreshToken)
    }

    fun getLibraries(libraries: MutableLiveData<List<Library>>) {
        database.child("users").child(prefs.readUid()).child("libraries")
            .addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun setListenerItemLibrary(library: Library, items: MutableLiveData<List<TMDBItem>>) {
        database.child("users").child(prefs.readUid()).child("libraries").child(library.id)
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


    fun getRandomContent(randomItems: MutableLiveData<MutableList<TMDBItem>>) {
        database.child("users").child(prefs.readUid()).child("libraries")
            .orderByChild("type")
            .equalTo("Peliculas").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listMovies = mutableListOf<Movie>()
                    val randomLocalItems = mutableListOf<TMDBItem>()

                    for (dataSnapShot in snapshot.children) {
                        val data = dataSnapShot.getValue(LibraryMovies::class.java)!!
                        listMovies.addAll(data.content.values.stream().collect(Collectors.toList()))
                    }

                    val size = 4
                    val s = HashSet<Int>(size)

                    while (s.size < size) {
                        s.add((0..listMovies.size).random())
                    }

                    s.stream().forEach { random ->
                        randomLocalItems.add(listMovies.get(random))
                    }
                    randomItems.postValue(randomLocalItems)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun getMostPopularMovies(popularMovies: MutableLiveData<MutableList<Movie>>) {
        database.child("users").child(prefs.readUid()).child("libraries")
            .orderByChild("type")
            .equalTo("Peliculas").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listMovies: MutableList<Movie> = mutableListOf()
                    val moviesNoRepeat:HashMap<String,Movie> = hashMapOf()
                    for (dataSnapShot in snapshot.children) {
                        val data = dataSnapShot.getValue(LibraryMovies::class.java)!!
                        val movies = data.content.values.stream().collect(Collectors.toList())
                        for(movie in movies){
                            moviesNoRepeat[movie.name] = movie
                        }
                    }
                    listMovies.addAll(moviesNoRepeat.values)
                    listMovies = listMovies.stream().sorted{movie1,movie2 -> (movie2.popularity!!*1000 - movie1.popularity!!*1000).toInt() }.collect(Collectors.toList()).subList(0,9)

                    popularMovies.postValue(listMovies)
                }

                override fun onCancelled(error: DatabaseError) {


                }
            })


    }
}
