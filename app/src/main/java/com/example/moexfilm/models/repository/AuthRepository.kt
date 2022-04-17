package com.example.moexfilm.models.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun firebaseSigninWithGoogle(googleAuthCredential: AuthCredential?): Boolean {
        var authenticated: Boolean = false
        firebaseAuth.signInWithCredential(googleAuthCredential!!)
            .addOnSuccessListener {
                authenticated = true
            }
            .await()
        return authenticated
    }

    suspend fun firebaseCreateUserWithEmailAndPassword(email: String, password: String): Boolean {
        var registered: Boolean = false
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    registered = true
                }.await()
        } catch (_: Exception) {
        }
        return registered
    }

    suspend fun firebaseSigninEmailPassword(email: String, password: String): Boolean {
        var authenticated: Boolean = false
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    authenticated = true
                }
                .await()
        }catch (_: Exception){
        }
        return authenticated
    }

}