package com.example.moexfilm.application

import android.app.Application
import com.example.moexfilm.R


class Application : Application() {
    companion object Access {
        lateinit var CLIENT_ID: String
        lateinit var CLIENT_SECRET: String
        lateinit var ACCESS_TOKEN: String
        const val GOOGLE_DRIVE_PLAY_URL:String = "https://www.googleapis.com/drive/v3/files/%s?alt=media"
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        CLIENT_ID = getString(R.string.oauth_client)
        CLIENT_SECRET = getString(R.string.oauth_secret)
        prefs = Prefs(this)
    }
}