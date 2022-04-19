package com.example.moexfilm.application

import android.app.Application
import com.example.moexfilm.R

class Application : Application() {
    companion object Access {
        lateinit var clientId: String
        lateinit var clientSecret: String
        lateinit var accessToken: String
        lateinit var refreshToken: String
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        clientId = getString(R.string.oauth_client)
        clientSecret = getString(R.string.oauth_secret)
        prefs = Prefs(this)
    }
}