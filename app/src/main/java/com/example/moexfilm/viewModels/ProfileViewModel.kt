package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.example.moexfilm.repositories.FirebaseDBRepository

class ProfileViewModel: ViewModel() {

    val librariesScanned:MutableLiveData<Int>  = MutableLiveData<Int>()
    val moviesScanned:MutableLiveData<Int>  = MutableLiveData<Int>()
    val tvShowsScanned:MutableLiveData<Int>  = MutableLiveData<Int>()

    init {
        getLibrariesScanned()
        getMoviesScanned()
        getTvShowsScanned()
    }

    private fun getLibrariesScanned() {
        FirebaseDBRepository.getLibrariesNumber(object:FirebaseDBCallBack{
            override fun onSuccess(item: Any) {
                val numbersLibraries = item as Int
                librariesScanned.postValue(numbersLibraries)
            }
            override fun onFailure() {}
        })
    }

    private fun getMoviesScanned() {
        FirebaseDBRepository.getMoviesNumber(object :FirebaseDBCallBack{
            override fun onSuccess(item: Any) {
                val numbersMovies = item as Int
                moviesScanned.postValue(numbersMovies)
            }
            override fun onFailure() {}
        })
    }

    private fun getTvShowsScanned() {
        FirebaseDBRepository.getTvShowsNumber(object :FirebaseDBCallBack{
            override fun onSuccess(item: Any) {
                val numbersTvShows = item as Int
                tvShowsScanned.postValue(numbersTvShows)
            }
            override fun onFailure() {}
        })
    }

}