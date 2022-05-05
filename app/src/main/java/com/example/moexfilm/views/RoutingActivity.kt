package com.example.moexfilm.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moexfilm.application.Application.Access.prefs
import com.example.moexfilm.util.StringUtil
import com.example.moexfilm.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(prefs.readUid().isEmpty())
        startActivity(Intent(this,LoginActivity::class.java))
        else
            startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}