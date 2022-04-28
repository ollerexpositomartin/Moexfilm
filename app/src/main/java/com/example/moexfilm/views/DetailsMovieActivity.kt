package com.example.moexfilm.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ActivityDetailsMovieBinding
import com.example.moexfilm.models.data.mediaObjects.Movie

class DetailsMovieActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsMovieBinding
    lateinit var movie:Movie
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

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