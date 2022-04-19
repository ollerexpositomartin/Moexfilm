package com.example.moexfilm.models.repository

import android.util.Log
import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.example.moexfilm.models.interfaces.services.TokenAuthService
import com.example.moexfilm.application.Application.Access.accessToken
import com.example.moexfilm.application.Application.Access.clientId
import com.example.moexfilm.application.Application.Access.clientSecret
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.helpers.RetrofitHelper

object TokenRepository {
    private val GOOGLE_URL_TOKEN = "https://oauth2.googleapis.com"

    suspend fun getTokens(accountId:String,authCode:String, idToken:String,callback:TokenCallBack) {
        //https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=
        val response = RetrofitHelper.getRetrofit(GOOGLE_URL_TOKEN).create(TokenAuthService::class.java)
            .getAccessToken("authorization_code", clientId,
                clientSecret,"",authCode,idToken,"offline","consent")

        if(response.isSuccessful){
            val token = response.body()
            if(token != null){
                accessToken = token.accessToken

                if(token.refreshToken != null){
                    val account = Account(accountId, token.refreshToken)
                    FirebaseDBRepository.saveAccountRefreshToken(account)
                }

                callback.onSucess()
                return
            }
        }
        callback.onFailure()
    }



}