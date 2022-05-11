package com.example.moexfilm.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.example.moexfilm.application.Application.Access.ACCESS_TOKEN
import com.example.moexfilm.application.Application.Access.GOOGLE_DRIVE_PLAY_URL
import com.example.moexfilm.databinding.ActivityVideoPlayerBinding
import com.example.moexfilm.models.data.mediaObjects.Episode
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.util.MediaUtil
import com.example.moexfilm.viewModels.VideoPlayerViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource


class VideoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var mediaSourceFactory: DefaultHttpDataSource.Factory
    private val videoPlayerViewModel: VideoPlayerViewModel by viewModels()
    private lateinit var content: TMDBItem
    private lateinit var videoUrl:String
    private lateinit var player: ExoPlayer
    private var startTime:Long = 0

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
        content = data!!.getSerializable("CONTENT")!! as TMDBItem
        startTime = data.getLong("PROGRESS")
        videoUrl = GOOGLE_DRIVE_PLAY_URL.format(content.idDrive)
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
        Log.d("STARTIME",startTime.toString())
        binding.playerView.player = player
        binding.playerView.keepScreenOn = true
        player.prepare()
        player.seekTo(startTime)
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

        if(player.currentPosition < player.duration - 300000)
        addToInProgress()
        else
            removeInProgress()


        player.stop()
        super.onDestroy()

    }

    private fun removeInProgress() {
        videoPlayerViewModel.removeMediaInProgress(content)
    }

    //METER UN RESULT FINISH PARA INDICAR CUANDO REFRESCAR EL HOMEFRAGMENT

    private fun addToInProgress() {
        if(content is Movie){
            val movie = content as Movie
            movie.duration = player.duration
            movie.playedTime = player.currentPosition
        }

        if(content is Episode){
            val episode = content as Episode
            episode.duration = player.duration
            episode.playedTime = player.currentPosition
        }

        videoPlayerViewModel.saveMediaInProgress(content)
    }

}