package com.example.moexfilm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.CookieManager
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.GOOGLE_DRIVE_PLAY_URL
import com.example.moexfilm.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
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
        setFullScreen()
        getData()
        setHeaders()
        startVideoPlayer()
        setListeners()
    }

    private fun setFullScreen() {
        window.apply {
            setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
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
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if(playbackState == Player.STATE_BUFFERING)
                    binding.loadIndicator.visibility = View.VISIBLE
                else if(playbackState == Player.STATE_READY) binding.loadIndicator.visibility = View.GONE
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)

            }
        })
    }

    private fun startVideoPlayer() {
        val defaultRenderersFactory = DefaultRenderersFactory(this).setExtensionRendererMode(EXTENSION_RENDERER_MODE_PREFER)
        player = ExoPlayer.Builder(this,defaultRenderersFactory).apply {
            setMediaSourceFactory(DefaultMediaSourceFactory(mediaSourceFactory))
        }.build()
        player.setMediaItem(MediaItem.fromUri(videoUrl))
        binding.playerView.player = player
        binding.playerView.keepScreenOn = true
        player.prepare()
        player.playWhenReady = true
    }


    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
        player.playbackState
    }

    override fun onRestart() {
        super.onRestart()
        player.playWhenReady = true
        player.playbackState
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

}