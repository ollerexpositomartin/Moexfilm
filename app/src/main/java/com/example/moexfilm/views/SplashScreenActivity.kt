package com.example.moexfilm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.clientId
import com.example.moexfilm.application.Application.Access.clientSecret
import com.example.moexfilm.application.Test


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}