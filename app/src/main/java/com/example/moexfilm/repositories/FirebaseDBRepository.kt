package com.example.moexfilm.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moexfilm.application.Application.Access.prefs
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.google.firebase.database.*

object FirebaseDBRepository {
    private const val FIREBASE_DB_URL = "https://moexfilm-default-rtdb.europe-west1.firebasedatabase.app/"
    private val dataBaseReference:FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_DB_URL)
    private var database:DatabaseReference

    init {
        dataBaseReference.setPersistenceEnabled(true)
        database = dataBaseReference.reference
    }

    fun createLibrary(library: Library, firebaseDBCallBack: FirebaseDBCallBack){
        database.child("users").child(prefs.readUid()).child("libraries").child(library.id).setValue(library)
            .addOnSuccessListener {
            firebaseDBCallBack.onSuccess(library)
        }
            .addOnFailureListener {
                firebaseDBCallBack.onFailure()
            }
    }

    fun saveMovieInLibrary(movie: Movie) {
        database.child("users").child(prefs.readUid()).child("libraries").child(movie.parent).child("content").child(movie.idDrive).setValue(movie)
    }

        fun saveAccountRefreshToken(account: Account) {
            database.child("users").child(prefs.readUid()).child("accounts")
                .child(account.id).setValue(account.refreshToken)
        }

        fun setListenerLibraries(libraries: MutableLiveData<List<Library>>) {
            database.child("users").child(prefs.readUid()).child("libraries")
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

    fun setListenerItemsLibrary(id:String,items: MutableLiveData<List<Movie>>) {
        database.child("users").child(prefs.readUid()).child("libraries").child(id).child("content")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Movie>()

                    for (dataSnapShot in snapshot.children) {
                        list.add(dataSnapShot.getValue(Movie::class.java)!!)
                    }
                    items.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    }
