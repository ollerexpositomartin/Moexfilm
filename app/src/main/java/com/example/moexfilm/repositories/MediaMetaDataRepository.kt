package com.example.moexfilm.repositories

import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.GOOGLE_DRIVE_PLAY_URL


object MediaMetaDataRepository {

    fun getMetaData(id:String){
        val retriever = MediaMetadataRetriever()
        val headers = HashMap<String,String>()
        headers["Authorization"] = "Bearer $ACCESS_TOKEN"
        Log.d("USU",GOOGLE_DRIVE_PLAY_URL.format(id))
        Log.d("ACCESS", ACCESS_TOKEN)

        retriever.setDataSource(GOOGLE_DRIVE_PLAY_URL.format(id),headers)

        val quality = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        //val f = MetaDataItem(quality,duration)
        Log.d("DURATION", duration.toString())
        Log.d("QUALITIY", quality.toString())
    }

}