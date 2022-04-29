package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.VideoMetaData

interface VideoMetadataCallBack {
    fun onSucess(videoMetadata:VideoMetaData)
    fun onFailure()
}