package com.example.moexfilm.models.repository

import com.example.moexfilm.models.interfaces.listeners.TokenCallBack
import com.example.moexfilm.models.interfaces.services.TokenAuthService
import com.example.moexfilm.util.Application.Access.accessToken
import com.example.moexfilm.util.Application.Access.clientId
import com.example.moexfilm.util.Application.Access.clientSecret
import com.example.moexfilm.util.Application.Access.refreshToken
import com.example.moexfilm.util.RetrofitHelper

object TokenRepository {
    private val GOOGLE_URL_TOKEN = "https://oauth2.googleapis.com"

    suspend fun getTokens(authCode:String, idToken:String,callback:TokenCallBack) {
        //https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=
        val response = RetrofitHelper.getRetrofit(GOOGLE_URL_TOKEN).create(TokenAuthService::class.java)
            .getAccessToken("authorization_code", clientId,
                clientSecret,"",authCode,idToken,"offline","consent")

        if(response.isSuccessful){
            val token = response.body()
            if(token!=null){
                accessToken = token.accessToken
                refreshToken = token.refreshToken
                callback.onSucess()
                return
            }
        }
        callback.onFailure()
    }
}