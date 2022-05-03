package com.example.moexfilm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.GOOGLE_DRIVE_PLAY_URL
import com.example.moexfilm.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource


class VideoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var mediaSourceFactory: DefaultHttpDataSource.Factory
    private lateinit var videoUrl:String
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //COMPROBAR TOKEN DE ACCESO Y SI NO ES VALIDO REFRESCARLO
        getData()
        setHeaders()
        startVideoPlayer()
        setListeners()
    }

    private fun getData() {
        val data = intent.extras
        val id  = data!!.getString("ID")!!
        videoUrl = GOOGLE_DRIVE_PLAY_URL.format(id)
    }

    private fun setHeaders(){
        mediaSourceFactory = DefaultHttpDataSource.Factory()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $ACCESS_TOKEN"
        mediaSourceFactory.setDefaultRequestProperties(headers)
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
        val defaultRenderersFactory = DefaultRenderersFactory(this).setExtensionRendererMode(EXTENSION_RENDERER_MODE_PREFER)
        player = ExoPlayer.Builder(this,defaultRenderersFactory).apply {
            setMediaSourceFactory(DefaultMediaSourceFactory(mediaSourceFactory))
        }.build()
        player.setMediaItem(MediaItem.fromUri(videoUrl))
        player.playWhenReady = true
        binding.playerView.player = player
    }
}