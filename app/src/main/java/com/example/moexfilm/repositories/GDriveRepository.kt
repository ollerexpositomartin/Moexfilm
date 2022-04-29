package com.example.moexfilm.repositories

import android.util.Log
import com.example.moexfilm.models.data.mediaObjects.GDriveItem
import com.example.moexfilm.models.data.responseObjects.GDriveResponse
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.services.GDriveService
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.models.helpers.RetrofitHelper
import com.example.moexfilm.models.interfaces.callBacks.VideoMetadataCallBack
import java.lang.Exception

object GDriveRepository {
    private const val GOOGLE_DRIVE_API_URL: String = "https://www.googleapis.com"

    suspend fun getChildItems(query:String, gDriveCallBack: GDriveCallBack) {
            var nextPageToken: String = ""
            var success: Boolean = true
            val folders: ArrayList<GDriveItem> = ArrayList()
            do {
                val response = RetrofitHelper.getRetrofit(GOOGLE_DRIVE_API_URL)
                    .create(GDriveService::class.java)
                    .getChildItems(
                        "allDrives",
                        query,
                        nextPageToken,
                        1000,
                        true,
                        true,
                        true,
                        "files(id,name),nextPageToken",
                        "Bearer $ACCESS_TOKEN"
                    )
                if (response.isSuccessful) {
                    val listDriveResponse: GDriveResponse = response.body()!!
                    folders.addAll(listDriveResponse.listGDriveItems)
                    nextPageToken = listDriveResponse.nextPageToken ?: ""
                } else {
                    nextPageToken = ""
                    success = false
                }
            } while (nextPageToken.isNotEmpty() && success)

            if (success) {
                gDriveCallBack.onSuccess(folders)
                return
            }
            gDriveCallBack.onFailure()
    }


    suspend fun getTeamDrives(gDriveCallBack: GDriveCallBack){
        var nextPageToken:String = ""
        var success:Boolean = true
        val teamDrives:ArrayList<GDriveItem> = ArrayList()
        do {
            val response = RetrofitHelper.getRetrofit(GOOGLE_DRIVE_API_URL).create(GDriveService::class.java)
                    .getTeamDrives(
                        "hidden = false",
                        nextPageToken,
                        100,
                        "Bearer $ACCESS_TOKEN"
                        )

            if(response.isSuccessful){
                val listDriveResponse: GDriveResponse = response.body()!!
                teamDrives.addAll(listDriveResponse.listGDriveItems)
                nextPageToken = listDriveResponse.nextPageToken?:""
            }else{
                nextPageToken = ""
                success = false
            }
        }while (nextPageToken.isNotEmpty() && success)

        if(success){
            gDriveCallBack.onSuccess(teamDrives)
            return
        }
        gDriveCallBack.onFailure()
    }

    suspend fun getVideoMetadata(id:String,callback:VideoMetadataCallBack){
        val response = RetrofitHelper.getRetrofit(GOOGLE_DRIVE_API_URL).create(GDriveService::class.java)
            .getVideoMetadata(
                id,
                true,
                "videoMediaMetadata",
                "Bearer $ACCESS_TOKEN"
            )

        if(response.isSuccessful){
            try {
                Log.d("ID",id)
                Log.d("RESPONSE", response.body()!!.toString())
                callback.onSucess(response.body()!!.videoMetadata)
            }catch (e:Exception){
                Log.d("Error",e.printStackTrace().toString())
            }
            return
        }
        callback.onFailure()

    }

}