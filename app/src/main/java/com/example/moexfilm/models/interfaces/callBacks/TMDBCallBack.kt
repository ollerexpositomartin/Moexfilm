package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.Movie

interface TMDBCallBack {
    fun onSearchItemCompleted(movie:Movie)
    fun onAllSearchsFinish()
}