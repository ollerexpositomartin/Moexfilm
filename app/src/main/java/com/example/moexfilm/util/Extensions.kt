package com.example.moexfilm.util

import android.view.View
import androidx.core.view.isVisible

val View.changeVisibility:View get() = apply { visibility = if(isVisible) View.INVISIBLE else View.VISIBLE }