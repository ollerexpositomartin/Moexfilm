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
import java.util.*

/**
 *Cambia el estado de visibilidad de la vista al estado contrario
 */
val View.changeVisibility:View get() = apply { visibility = if(isVisible) View.INVISIBLE else View.VISIBLE }

/**
 *Carga en el imageView la url usando Glide
 * @param url enlace a la imagen
 */
fun ImageView.loadImage(url:String){ Glide.with(context).load(url).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(this) }

/**
 *Cambia la longitud maxima de un texto realizando una animación
 */
fun TextView.expandCollapseTextView() {
    if(this.maxLines == this.lineCount)
        ObjectAnimator.ofInt(this, "maxLines",3).apply {
            setDuration(100).start()
        }

    if(this.maxLines != this.lineCount)
        ObjectAnimator.ofInt(this, "maxLines",this.lineCount).apply {
            setDuration(100).start()
        }
}

/**
 * Redondea el valor a un decimal
 */
fun Double.round():Double = Math.round(this * 10.0) / 10.0

/**
 * Establece el primer caracter del String a mayuscula
 */
fun String.capitalize():String {
        if(this.isEmpty()) {
            return this;
        }
        return this.substring(0, 1).uppercase(Locale.getDefault()) + this.substring(1);
    }




