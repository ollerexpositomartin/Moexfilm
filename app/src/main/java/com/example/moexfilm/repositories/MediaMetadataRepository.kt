package com.example.moexfilm.repositories

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.core.net.toUri
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.GOOGLE_DRIVE_PLAY_URL
import com.example.moexfilm.models.data.mediaObjects.VideoMetaData

/**
 * NO SE USA EN ESTE MOMENTO
 */
object MediaMetadataRepository {

    fun getMediaMetadata(id:String):VideoMetaData{

        val retriever = MediaMetadataRetriever()
        val uri  = GOOGLE_DRIVE_PLAY_URL.format(id)
        val headers = HashMap<String,String>()
        headers["Authorization"] = "Bearer $ACCESS_TOKEN"

        retriever.setDataSource(uri,headers)

        val quality:Long = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toLong()?:0
        val duration:Long = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong()?:0
        Thread.sleep(3000)
        return VideoMetaData(quality,duration)
    }

}