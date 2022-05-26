package com.example.moexfilm.models.interfaces.listeners

import com.example.moexfilm.models.data.mediaObjects.Library

/**
 * Interfaz para escuchar el estado del servicio de escaneo
 */
interface ServiceListener {
    fun isRunning(libraryItemList:MutableList<Library>)
}