package com.example.moexfilm.models.interfaces

/**
 * Interfaz que implementan los elementos que pueden reproducirse
 */
interface Playable {
    fun duration():Long
    fun playedTime():Long
}