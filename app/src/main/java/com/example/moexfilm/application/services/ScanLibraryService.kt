package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast


class ScanLibraryService: Service() {

    inner class LocalBinder : Binder() {
        fun getService():ScanLibraryService{
            return this@ScanLibraryService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(this, "Service OnBind()", Toast.LENGTH_LONG).show()
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed ", Toast.LENGTH_SHORT).show()
    }


    private val mBinder = LocalBinder()

    override fun onUnbind(intent: Intent?): Boolean {

        return super.onUnbind(intent)
    }




}

