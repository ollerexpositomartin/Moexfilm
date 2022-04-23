package com.example.moexfilm.repositories

import androidx.lifecycle.MutableLiveData
import com.example.moexfilm.application.Application.Access.prefs
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.Movie
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

/*
TEST
  val values: HashMap<String, Any> = HashMap()
        values.put("content", listOf(1,2,3))

        database.child("users").child("1234").child("Libraries").child("idBiblioteca").updateChildren(values)

        database.child("users").child("1234").child("Libraries").push().setValue(library)
 */

    fun createLibrary(library:Library,firebaseDBCallBack: FirebaseDBCallBack){
        database.child("users").child(prefs.readUid()).child("libraries").child(library.id).setValue(library)
            .addOnSuccessListener {
            firebaseDBCallBack.onSuccess(library)
        }
            .addOnFailureListener {
                firebaseDBCallBack.onFailure()
            }
    }

    fun saveMovieInLibrary(idLibrary:String,movie:Movie){
        val contentReference = database.child("users").child(prefs.readUid()).child("libraries").child(idLibrary).child("content")
        val key = contentReference.push().key
        val map: MutableMap<String, Any> = HashMap()
        map[key!!] = movie
        contentReference.updateChildren(map)
    }

    fun saveAccountRefreshToken(account:Account){
            database.child("users").child(prefs.readUid()).child("accounts")
                .child(account.id).setValue(account.refreshToken)
    }

    fun setListenerLibraries(libraries:MutableLiveData<List<Library>>){
        database.child("users").child(prefs.readUid()).child("libraries").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Library>()

                for(dataSnapShot in snapshot.children){
                    list.add(dataSnapShot.getValue(Library::class.java)!!)
                }
                libraries.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    //HABRIA QUE CAMBIAR ESTE METODO A OTRA CLASE YA QUE EL NO REALIZA NINGUNA LLAMADA ??
    //fun getLibraries():Query{
        //return database.child("users").child(prefs.readUid()).child("libraries").orderByKey()
   // }

}