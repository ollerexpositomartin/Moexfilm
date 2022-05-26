package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.GDriveItem

/**
 * Interfaz para los callbacks de Google Drive
 */
interface GDriveCallBack {
    fun onSuccess(response:ArrayList<GDriveItem>?)
    fun onFailure()
}