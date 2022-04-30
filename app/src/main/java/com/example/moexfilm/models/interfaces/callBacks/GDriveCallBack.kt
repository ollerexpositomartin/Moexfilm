package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.GDriveItem

interface GDriveCallBack {
    fun onSuccess(response:ArrayList<GDriveItem>?)
    fun onFailure()
}