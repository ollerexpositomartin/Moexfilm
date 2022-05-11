package com.example.moexfilm.views

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.moexfilm.R
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.interfaces.Likable
import com.example.moexfilm.viewModels.LikeViewModel

abstract class LikeActivity:AppCompatActivity() {
    lateinit var btnLike: LottieAnimationView
    lateinit var media: TMDBItem
    private val likeViewModel: LikeViewModel by viewModels()

     fun btnLikeListener() {
         btnLike.setMaxProgress(0.60F)
        btnLike.setOnClickListener {
            val likable:Likable = media as Likable

            if (!likable.obtainLike()) {
                btnLike.playAnimation()
                likable.assingLike(true)
            } else {
                btnLike.progress = 0F
                likable.assingLike(false)
            }
            likeViewModel.likeMedia(media)
        }
    }

    fun checkLike() {
        val likable: Likable = media as Likable
        if (likable.obtainLike()) {
            btnLike.progress = 0.60F
            return
        }
        btnLike.progress = 0F
    }


}