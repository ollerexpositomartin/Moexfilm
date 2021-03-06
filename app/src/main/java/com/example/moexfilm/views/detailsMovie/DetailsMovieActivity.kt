package com.example.moexfilm.views.detailsMovie

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.expandCollapseTextView
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ActivityDetailsMovieBinding
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.interfaces.Likable
import com.example.moexfilm.util.MediaUtil
import com.example.moexfilm.util.StringUtil
import com.example.moexfilm.viewModels.DetailsMovieViewModel
import com.example.moexfilm.viewModels.LikeViewModel
import com.example.moexfilm.views.LikeActivity
import com.example.moexfilm.views.VideoPlayerActivity
import com.example.moexfilm.views.detailsMovie.adapters.CastAdapter


class DetailsMovieActivity : LikeActivity() {
    lateinit var binding: ActivityDetailsMovieBinding
    private lateinit var movie: Movie
    private lateinit var language:String
    private var expandOrCollapse:Boolean = true
    private lateinit var detailsMovieViewModel:DetailsMovieViewModel

    private lateinit var adapter:CastAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        btnLike = binding.btnLike
        setContentView(binding.root)
        setTransparentStatusBar()
        getData()
        detailsMovieViewModel = DetailsMovieViewModel(movie)
        setRecycler()
        initCastObserver()
        checkLike()
        setListeners()
    }


    private fun setListeners() {
        binding.btnEspandCollapse.setOnClickListener { expandCollapse() }
        binding.btnPlay.setOnClickListener {
            startActivity(Intent(this, VideoPlayerActivity::class.java).apply {
                putExtra("CONTENT", movie)
            })
        }
        binding.btnBack.setOnClickListener { finish() }

        btnLikeListener()
    }


    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setRecycler() {
        adapter = CastAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initCastObserver() {
        detailsMovieViewModel.mutableListCast.observe(this){ actors ->
                adapter.submitList(actors)
        }
    }

    private fun expandCollapse() {
        binding.tvOverview.expandCollapseTextView()
        expandOrCollapse = if(expandOrCollapse) {
            binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_up)
            false
        } else{
            binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_down)
            true
        }
    }

    private fun getData() {
        val data = intent.extras
        movie = data!!.getSerializable("MOVIE") as Movie
        media = movie
        binding.imvBackground.loadImage(TMDB_IMAGE_URL.format(movie.backdrop_path))
        binding.imvPoster.loadImage(TMDB_IMAGE_URL.format(movie.poster_path))
        binding.tvNameMovie.text = movie.name
        binding.tvAverage.text = movie.vote_average.toString()
        binding.tvOverview.text = movie.overview
        binding.tvFileName.text = movie.fileName
        binding.tvDate.text = movie.release_date
        binding.tvDuration.text = MediaUtil.minToHoursAndMinutes(MediaUtil.msToMinutes(movie.duration))
        binding.tvGenres.text = StringUtil.genresToString(movie.genres)
    }
}