package com.example.moexfilm.models.interfaces.listeners

import com.example.moexfilm.models.data.Library

interface ServiceListener {
    fun isRunning(libraryItemList:MutableList<Library>)
}