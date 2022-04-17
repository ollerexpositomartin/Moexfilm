package com.example.moexfilm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moexfilm.R
import com.example.moexfilm.util.Application.Access.clientId
import com.example.moexfilm.util.Application.Access.clientSecret


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientId = getString(R.string.oauth_client)
        clientSecret = getString(R.string.oauth_secret)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}