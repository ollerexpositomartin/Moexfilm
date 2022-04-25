package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.Library

interface FirebaseDBCallBack {
    fun onSuccess(library: Library)
    fun onFailure()
}