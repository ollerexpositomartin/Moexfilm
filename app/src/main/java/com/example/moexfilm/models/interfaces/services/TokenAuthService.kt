package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.Token
import com.google.firebase.inject.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TokenAuthService {
    @POST("token")
    suspend fun getAccessToken(
        @Query("grant_type") grant_type: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("code") authCode: String,
        @Query("id_token") id_token: String,
        @Query("access_type") access_type:String,
        @Query("prompt") prompt:String
    ): Response<Token>
}