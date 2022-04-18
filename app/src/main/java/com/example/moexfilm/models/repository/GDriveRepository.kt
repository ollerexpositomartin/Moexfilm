package com.example.moexfilm.models.repository

import android.util.Log
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.models.interfaces.services.GDriveService
import com.example.moexfilm.util.Application.Access.accessToken
import com.example.moexfilm.util.RetrofitHelper

object GDriveRepository {
    val CHILD_FOLDERS_URL:String = "https://www.googleapis.com"
    val CHILD_TEAMDRIVE_URL:String = ""

    suspend fun getChildFolders(item:GDriveElement){
        var nextPageToken:String = ""
        var success:Boolean = true
        val folders:ArrayList<GDriveElement> = ArrayList()
            do {
                Log.d("ACCESSTOKEN", accessToken)
                val response =
                    RetrofitHelper.getRetrofit(CHILD_FOLDERS_URL).create(GDriveService::class.java)
                        .getChildFolders(
                            "'${item.id}' in parents and mimeType = 'application/vnd.google-apps.folder'",
                            nextPageToken,
                            1000,
                            true,
                            true,
                            true,
                            "files(id,name),nextPageToken",
                            "Bearer $accessToken"
                        )
                if (response.isSuccessful) {
                    val responseListDrive = response.body()
                    folders.addAll(responseListDrive!!.ListGDriveElements)
                    nextPageToken = responseListDrive.nextPageToken
                    Log.d("BIEN---->",response.message())
                } else {
                    nextPageToken = ""
                    success = false
                    Log.d("MAL---->",response.raw().toString())
                }
            } while (nextPageToken.isNotEmpty() && success)

            if (success) {
                Log.d("RESULTADO----->", folders.toString())
            }

    }
}