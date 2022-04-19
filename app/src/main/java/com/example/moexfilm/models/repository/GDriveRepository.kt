package com.example.moexfilm.models.repository

import android.util.Log
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.models.data.ResponseGDrive
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.services.GDriveService
import com.example.moexfilm.util.Application.Access.accessToken
import com.example.moexfilm.util.RetrofitHelper

object GDriveRepository {
    private const val GOOGLE_DRIVE_API_URL: String = "https://www.googleapis.com"

    suspend fun getChildFolders(item: GDriveElement,gDriveCallBack: GDriveCallBack) {
        var nextPageToken: String = ""
        var success: Boolean = true
        val folders: ArrayList<GDriveElement> = ArrayList()
        do {
            val response = RetrofitHelper.getRetrofit(GOOGLE_DRIVE_API_URL).create(GDriveService::class.java)
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
                    val responseListDrive:ResponseGDrive = response.body()!!
                    folders.addAll(responseListDrive.ListGDriveElements)
                    nextPageToken = responseListDrive.nextPageToken ?: ""
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
        val teamDrives:ArrayList<GDriveElement> = ArrayList()
        Log.d("accessToken", accessToken)
        do {
            val response = RetrofitHelper.getRetrofit(GOOGLE_DRIVE_API_URL).create(GDriveService::class.java)
                    .getTeamDrives(
                        "hidden = false",
                        nextPageToken,
                        100,
                        "Bearer $accessToken"
                        )

            if(response.isSuccessful){
                val responseListDrive:ResponseGDrive = response.body()!!
                teamDrives.addAll(responseListDrive.ListGDriveElements)
                nextPageToken = responseListDrive.nextPageToken?:""
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

}