package com.example.moexfilm.models.interfaces.listeners

import com.example.moexfilm.models.data.ScanItem

interface ServiceListener {
    fun isRunning(scanItemList:MutableList<ScanItem>)
}