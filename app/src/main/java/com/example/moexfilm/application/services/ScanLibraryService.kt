package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ScanLibraryService:Service() {

    private val inExecution:ArrayList<String> = ArrayList()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun isExecuting():Boolean{
        return inExecution.size > 0
    }

}