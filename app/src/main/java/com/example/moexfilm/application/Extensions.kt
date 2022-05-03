package com.example.moexfilm.application

import android.animation.ObjectAnimator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

val View.changeVisibility:View get() = apply { visibility = if(isVisible) View.INVISIBLE else View.VISIBLE }

fun ImageView.loadImage(url:String){ Glide.with(context).load(url).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(this) }


fun TextView.expandCollapseTextView() {
    if(this.maxLines == this.lineCount)
        ObjectAnimator.ofInt(this, "maxLines",3).apply {
            setDuration(150).start()
        }

    if(this.maxLines != this.lineCount)
        ObjectAnimator.ofInt(this, "maxLines",this.lineCount).apply {
            setDuration(150).start()
        }
}

fun Double.round():Double = Math.round(this * 10.0) / 10.0


