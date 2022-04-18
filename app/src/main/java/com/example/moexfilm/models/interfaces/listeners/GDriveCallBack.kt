package com.example.moexfilm.models.interfaces.listeners

import com.example.moexfilm.models.data.GDriveElement

interface GDriveCallBack {
    fun onSuccess(response:ArrayList<GDriveElement>)
    fun onFailure()
}