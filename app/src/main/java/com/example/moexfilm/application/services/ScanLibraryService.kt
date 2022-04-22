package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.moexfilm.databinding.ActivityMainBinding
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.ScanItem
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.repositories.GDriveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import kotlin.properties.Delegates


class ScanLibraryService : Service() {
    private lateinit var serviceListener:ServiceListener
    private val currentItemScanning: MutableList<ScanItem> = mutableListOf()
    private val queryFormat = "'%s' in parents"
    private var isScanning:Boolean by Delegates.observable(false){ _, _, newValue ->
        serviceListener.isRunning()
    }

    inner class LocalBinder : Binder() {
        fun getService(): ScanLibraryService {
            return this@ScanLibraryService
        }
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    fun setScanServiceListener(serviceListener:ServiceListener){
        this.serviceListener = serviceListener
    }

    fun startScan(scanItem: ScanItem) {
        isScanning = true

        CoroutineScope(Dispatchers.IO).launch {
            val foldersToScan: MutableList<GDriveItem> = scanItem.subFolders ?: mutableListOf()
            val query:StringBuilder = StringBuilder()
            var files:List<GDriveItem> = emptyList()
            foldersToScan.add(GDriveItem(scanItem.id, "CurrentElement"))
            currentItemScanning.add(scanItem)

            for (i in foldersToScan.indices) {
                if (i == 1 || i == foldersToScan.size - 1)
                    query.append(queryFormat.format(foldersToScan[i].id))

                if (i != foldersToScan.size - 1)
                    query.append(queryFormat.format(foldersToScan[i].id)).append(" or")
            }

            GDriveRepository.getChildItems(query.toString(),object: GDriveCallBack{
                override fun onSuccess(response: ArrayList<GDriveItem>) {
                    files = response
                    Log.d("FILES",files.toString())
                }

                override fun onFailure() {
                    Log.d("FAILURE","FAILURE")
                }
            })

        }

    }


    private val mBinder = LocalBinder()

}

