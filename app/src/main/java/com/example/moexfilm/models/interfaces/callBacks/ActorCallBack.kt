package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.Cast

interface ActorCallBack {
    fun onSuccess(casts: List<Cast>)
}