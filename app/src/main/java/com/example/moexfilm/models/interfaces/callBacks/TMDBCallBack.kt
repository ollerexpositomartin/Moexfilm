package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.MediaItem
import com.example.moexfilm.models.data.mediaObjects.Movie

interface TMDBCallBack {
    fun onSearchItemCompleted(itemTMDB:MediaItem)
    fun onAllSearchsFinish()
}