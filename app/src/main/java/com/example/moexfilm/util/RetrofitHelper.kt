package com.example.moexfilm.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(url:String):Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    }
}