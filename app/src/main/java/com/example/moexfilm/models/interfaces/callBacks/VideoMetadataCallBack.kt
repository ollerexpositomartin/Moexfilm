package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.mediaObjects.VideoMetaData

/**
 * Interfaz para los callbacks de los metadatos de un video
 */
interface VideoMetadataCallBack {
    fun onSucess(videoMetadata:VideoMetaData)
    fun onFailure()
}