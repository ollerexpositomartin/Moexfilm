package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.ResponseGDrive
import com.example.moexfilm.util.Application
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GDriveService {
    @GET("/drive/v3/files")
    suspend fun getChildFolders(
        @Query("q") q: String,
        @Query("pageToken") pageToken: String,
        @Query("pageSize") pageSize: Int,
        @Query("supportsTeamDrive") supportsTeamDrive: Boolean,
        @Query("includeItemsFromAllDrives") includeItemsFromAllDrives:Boolean,
        @Query("supportsAllDrives") supportsAllDrives:Boolean,
        @Query("fields") fields: String,
        @Header("Authorization") accessToken:String
    ): Response<ResponseGDrive>
}