package com.example.moexfilm.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.expandCollapseTextView
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ActivityDetailsMovieBinding
import com.example.moexfilm.models.data.mediaObjects.Movie


class DetailsMovieActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsMovieBinding
    private lateinit var movie:Movie
    private var expandOrCollapse:Boolean = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnEspandCollapse.setOnClickListener{
            binding.tvOverview.expandCollapseTextView()
            expandOrCollapse = if(expandOrCollapse) {
                binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_up)
                false
            } else{
                binding.btnEspandCollapse.setImageResource(R.drawable.ic_arrow_down)
                true
            }
        }
        getData()
    }


    private fun getData() {
        val data = intent.extras
        movie = data!!.getSerializable("MOVIE") as Movie
        Log.d("MOVIE", movie.backdrop_path.toString())
        binding.imvBackground.loadImage(TMDB_IMAGE_URL.format(movie.backdrop_path))
        binding.imvPoster.loadImage(TMDB_IMAGE_URL.format(movie.poster_path))
        binding.tvNameMovie.text = movie.name
        binding.tvAverage.text = movie.vote_average.toString()
        binding.tvOverview.text = movie.overview
    }
}