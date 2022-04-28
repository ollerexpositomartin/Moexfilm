package com.example.moexfilm.views

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.moexfilm.application.services.ScanLibraryService
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.interfaces.listeners.ServiceListener

abstract class ScanActivity : AppCompatActivity(), ServiceListener {
    private lateinit var connection: ServiceConnection
    var service: ScanLibraryService? = null
    lateinit var library: Library

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       startConnection()
    }

    private fun startConnection() {
        connection = object:ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                service = (p1 as ScanLibraryService.LocalBinder).getService()
                service!!.setScanServiceListener(this@ScanActivity)
                service!!.startScan(library)
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
            }
        }
    }

    fun initService() {
        if(service == null){
            Intent(this, ScanLibraryService::class.java).also { service ->
                startService(service)
                bindService(service, connection, BIND_AUTO_CREATE)
            }
        }else {
            service!!.startScan(library)
        }
    }


}