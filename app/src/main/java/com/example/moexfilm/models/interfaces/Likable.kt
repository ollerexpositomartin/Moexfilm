package com.example.moexfilm.models.interfaces

/**
 * Interfaz que implementan los modelos que pueden ser "liked"
 */
interface Likable {
    fun assingLike(like:Boolean)
    fun obtainLike(): Boolean
}