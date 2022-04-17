package com.example.moexfilm.util

import android.app.Application

class Application : Application() {
    companion object Access {
        lateinit var clientId: String
        lateinit var clientSecret: String
        lateinit var accessToken: String
        lateinit var refreshToken: String
    }
}