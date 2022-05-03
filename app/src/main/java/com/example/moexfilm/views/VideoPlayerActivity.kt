package com.example.moexfilm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moexfilm.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player


class VideoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayerBinding
    lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startVideoPlayer()
        setListeners()
    }

    private fun setListeners() {
        player.addListener(object:Player.Listener{
            override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
                //COMPROBAR TOKEN DE ACCESO Y SI NO ES VALIDO REFRESCARLO
                super.onSeekBackIncrementChanged(seekBackIncrementMs)
            }
        })
    }

    private fun startVideoPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
    }


}