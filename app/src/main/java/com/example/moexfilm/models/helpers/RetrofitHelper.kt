package com.example.moexfilm.models.helpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Clase para la creación de una instancia de Retrofit
 */
object RetrofitHelper {
    /**
     * Crea una instancia de Retrofit con la url que le pasemos
     * @param url Url de la api
     */
    fun getRetrofit(url:String):Retrofit{
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    }
}