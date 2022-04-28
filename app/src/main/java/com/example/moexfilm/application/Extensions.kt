package com.example.moexfilm.application

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

val View.changeVisibility:View get() = apply { visibility = if(isVisible) View.INVISIBLE else View.VISIBLE }

fun ImageView.loadImage(url:String){ Glide.with(context).load(url).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(this) }


