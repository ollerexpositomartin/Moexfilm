package com.example.moexfilm.repositories

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Clase que contiene los metodos para poder autenticar y crear un usuario
 */
object AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    /**
     * Metodo que permite a un usuario de Google registrarse y loguearse en caso de que exista
     * @param googleAuthCredential Credencial de Google
     * @return Devuelve si el usuario se ha logueado o no
     */
    suspend fun firebaseSigninWithGoogle(googleAuthCredential: AuthCredential?): Boolean {
        var authenticated: Boolean = false
        firebaseAuth.signInWithCredential(googleAuthCredential!!)
            .addOnSuccessListener {
                authenticated = true
            }
            .await()
        return authenticated
    }

    /**
     * Metodo que permite crear un usuario usando su email y su password
     * @param email email del usuario
     * @param password password del usuario
     * @return Devuelve si el usuario se ha registrado correctamente o no
     */
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

    /**
     * Metodo que permite a un usuario logearse usando su email y su password
     * @param email email del usuario
     * @param password password del usuario
     * @return Devuelve si el usuario se ha logeado correctamente o no
     */
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