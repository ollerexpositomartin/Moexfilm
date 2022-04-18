package com.example.moexfilm.util

import android.view.View
import androidx.core.view.isVisible

val View.changeVisibility:View get() = apply { if(isVisible) visibility = View.INVISIBLE else visibility = View.VISIBLE  }