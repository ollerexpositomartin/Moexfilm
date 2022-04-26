package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.TMDBItem

interface TMDBCallBack {
    fun onSearchItemCompleted(itemTMDB:TMDBItem)
    fun onAllSearchsFinish()
}