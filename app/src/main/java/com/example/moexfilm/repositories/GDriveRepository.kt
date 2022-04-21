package com.example.moexfilm.repositories

import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.ResponseGDrive
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.services.GDriveService
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.models.helpers.RetrofitHelper

object GDriveRepository {
    private const val GOOGLE_DRIVE_API_URL: String = "https://www.googleapis.com"

    suspend fun getChildFolders(item: GDriveItem, gDriveCallBack: GDriveCallBack) {
        var nextPageToken: String = ""
        var success: Boolean = true
        val folders: ArrayList<GDriveItem> = ArrayList()
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
                        "Bearer $ACCESS_TOKEN"
                    )
            if (response.isSuccessful) {
                    val responseListDrive:ResponseGDrive = response.body()!!
                    folders.addAll(responseListDrive.listGDriveItems)
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
                val responseListDrive:ResponseGDrive = response.body()!!
                teamDrives.addAll(responseListDrive.listGDriveItems)
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