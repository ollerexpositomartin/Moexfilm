package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.Cast
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.repositories.FirebaseDBRepository
import com.example.moexfilm.repositories.TMDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.stream.Collectors

class DetailsMovieViewModel(val movie: Movie, val language: String) : ViewModel() {
    val mutableListCast = MutableLiveData<List<Cast>>()

    init{
        getCastMovie()
    }

    private fun getCastMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            TMDBRepository.getMovieCast(movie, language) { castReceived(it) }
        }
    }

    private fun castReceived(cast: List<Cast>) {
        cast.stream().filter { it.department == "Acting"}.collect(Collectors.toList()).apply {
            mutableListCast.postValue(this)
        }
    }

}