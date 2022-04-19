package com.example.moexfilm.models.repository

import com.example.moexfilm.models.data.Library
import com.example.moexfilm.util.FirebaseUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseDBRepository {
    private const val FIREBASE_DB_URL = "https://moexfilm-default-rtdb.europe-west1.firebasedatabase.app"
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

    fun createLibrary(library:Library){
        database.child("users").child(FirebaseUtil.getUid()).child("libraries").child(library.id).setValue(library)

    }

}