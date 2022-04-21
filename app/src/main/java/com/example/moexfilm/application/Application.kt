package com.example.moexfilm.application

import android.app.Application
import com.example.moexfilm.R


class Application : Application() {
    companion object Access {
        lateinit var CLIENT_ID: String
        lateinit var CLIENT_SECRET: String
        lateinit var ACCESS_TOKEN: String
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        CLIENT_ID = getString(R.string.oauth_client)
        CLIENT_SECRET = getString(R.string.oauth_secret)
        prefs = Prefs(this)
    }
}