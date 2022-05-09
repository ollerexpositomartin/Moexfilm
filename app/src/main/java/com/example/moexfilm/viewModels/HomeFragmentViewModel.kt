package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class HomeFragmentViewModel: ViewModel() {
    val mutableListRandomItemsMutableLiveData:MutableLiveData<MutableList<TMDBItem>> = MutableLiveData()
    val mutableListPopularMoviesMutableLiveData:MutableLiveData<MutableList<Movie>> = MutableLiveData()

    init {
        loadRandomContent()
        loadPopularMovies()
    }

    private fun loadRandomContent(){
        FirebaseDBRepository.getRandomContent(mutableListRandomItemsMutableLiveData)
    }

    private fun  loadPopularMovies(){
        FirebaseDBRepository.getMostPopularMovies(mutableListPopularMoviesMutableLiveData)
    }


}