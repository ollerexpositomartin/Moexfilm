package com.example.moexfilm.models.interfaces.callBacks
import com.example.moexfilm.models.data.mediaObjects.Library

/**
 * Interfaz para los callbacks de FirebaseDB
 */
interface FirebaseDBCallBack {
    fun onSuccess(item: Any)
    fun onFailure()
}