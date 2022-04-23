package com.example.moexfilm.application.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.ScanItem
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.repositories.GDriveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder
import kotlin.properties.Delegates


class ScanLibraryService : Service() {
    private lateinit var serviceListener: ServiceListener
    private var currentItemsScanning: MutableList<ScanItem> by Delegates.observable(mutableListOf()) { _, _, newValue ->
        runBlocking(Dispatchers.Main){
            serviceListener.isRunning(newValue)
        }
    }
    private val queryFormat = "parents in '%s'"

    inner class LocalBinder : Binder() {
        fun getService(): ScanLibraryService {
            return this@ScanLibraryService
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    private val mBinder = LocalBinder()

    fun setScanServiceListener(serviceListener: ServiceListener) {
        this.serviceListener = serviceListener
    }

    fun startScan(scanItem: ScanItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val foldersToScan: MutableList<GDriveItem> = scanItem.subFolders ?: mutableListOf()
            val query: StringBuilder = StringBuilder()
            var files: List<GDriveItem> = emptyList()
            foldersToScan.add(GDriveItem(scanItem.name, scanItem.id))

            currentItemsScanning.add(scanItem)
            currentItemsScanning = currentItemsScanning

            for (i in foldersToScan.indices) {
                query.append(queryFormat.format(foldersToScan[i].id))

                if (foldersToScan.size > 1 && i != foldersToScan.size - 1)
                    query.append(" or ")
            }
            query.append("and mimeType = 'video/x-matroska'")

            GDriveRepository.getChildItems(query.toString(), object : GDriveCallBack {
                override fun onSuccess(response: ArrayList<GDriveItem>) {
                    files = response
                    Log.d("FILES", files.toString())
                }

                override fun onFailure() {
                    Log.d("FAILURE", "FAILURE")
                }
            })

        }

    }
}

