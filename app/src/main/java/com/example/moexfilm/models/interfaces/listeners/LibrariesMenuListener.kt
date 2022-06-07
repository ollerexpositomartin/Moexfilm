package com.example.moexfilm.models.interfaces.listeners

import android.view.View
import com.example.moexfilm.models.data.mediaObjects.Library

/**
 * Interfaz para escuchar los click del usuario en la lista de librerias
 */
interface LibrariesMenuListener {
    /**
     * Metodo para escuchar el click en una libreria
     * @param library Libreria seleccionada
     */
    fun onLibraryClick(library: Library)
    /**
     * Metodo para escuchar el click en el boton de menu
     * @param position posicion de la libreria en la lista
     */
    fun onMenuClick(positionMenu:Int)
}