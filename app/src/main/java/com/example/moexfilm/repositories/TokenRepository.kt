package com.example.moexfilm.repositories

import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.example.moexfilm.models.interfaces.services.TokenAuthService
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.CLIENT_ID
import com.example.moexfilm.application.Application.Access.CLIENT_SECRET
import com.example.moexfilm.application.Application.Access.REFRESH_TOKEN
import com.example.moexfilm.models.data.Account
import com.example.moexfilm.models.data.Token
import com.example.moexfilm.models.helpers.RetrofitHelper
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import kotlinx.coroutines.*

object TokenRepository {
    private val GOOGLE_URL_TOKEN = "https://oauth2.googleapis.com"

    suspend fun getTokens(accountId:String,authCode:String, idToken:String,callback:TokenCallBack) {
        //https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=
        val response = RetrofitHelper.getRetrofit(GOOGLE_URL_TOKEN).create(TokenAuthService::class.java)
            .getAccessToken("authorization_code", CLIENT_ID,
                CLIENT_SECRET,"",authCode,idToken,"offline","consent")

        if(response.isSuccessful){
            val token = response.body()
            if(token != null){
                ACCESS_TOKEN = token.accessToken!!

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

    suspend fun getTokens(libraryId: String,callback:TokenCallBack) {
        FirebaseDBRepository.getAccountId(libraryId,object:FirebaseDBCallBack{
            override fun onSuccess(item: Any) {
                    val owner = item as String
                    FirebaseDBRepository.getRefreshToken(owner,object :TokenCallBack{
                        override fun onSucess() {
                            CoroutineScope(Dispatchers.IO).launch {
                                val response = RetrofitHelper.getRetrofit(GOOGLE_URL_TOKEN)
                                    .create(TokenAuthService::class.java)
                                    .getAccessToken(
                                        "refresh_token", CLIENT_ID,
                                        CLIENT_SECRET, REFRESH_TOKEN
                                    )
                                if (response.isSuccessful) {
                                    val responseToken = response.body()!!
                                    ACCESS_TOKEN = responseToken.accessToken!!
                                    withContext(Dispatchers.Main) { callback.onSucess() }
                                }
                            }
                        }
                        override fun onFailure() {} })
                }
            override fun onFailure() {} })
    }



}