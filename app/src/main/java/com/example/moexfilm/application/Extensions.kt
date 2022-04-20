package com.example.moexfilm.application

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.view.View
import androidx.core.view.isVisible

val View.changeVisibility:View get() = apply { visibility = if(isVisible) View.INVISIBLE else View.VISIBLE }

fun <T> Context.isServiceRunning(service: Class<T>) =
    (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == service.name }