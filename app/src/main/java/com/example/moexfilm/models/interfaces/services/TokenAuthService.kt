package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.Token
import com.google.firebase.inject.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TokenAuthService {

    /**
     * Obtiene el token de acceso a Google Drive usando un authCode
     * @param grant_type Indica de la forma que se va a obtener el token
     * @param client_id El ID de cliente
     * @param client_secret El secreto de cliente
     * @param redirect_uri uri de redireccion listada en el proyecto
     * @param authCode El codigo de autorizacion
     * @param id_token El idToken
     * @param access_type Indica si su aplicación puede actualizar tokens de acceso cuando el usuario no está presente en el navegador
     * @return
     */
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

    /**
     * Obtiene el token de acceso a Google Drive usando un refreshToken
     * @param grant_type Indica de la forma que se va a obtener el token
     * @param client_id El ID de cliente
     * @param client_secret El secreto de cliente
     * @param refresh_token El refreshToken
     * @return
     */
    @POST("token")
    suspend fun getAccessToken(
        @Query("grant_type") grant_type: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("refresh_token") refresh_token: String,
    ): Response<Token>
}