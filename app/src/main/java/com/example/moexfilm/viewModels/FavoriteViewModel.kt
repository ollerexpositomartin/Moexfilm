package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class FavoriteViewModel: ViewModel() {
    val mutableLikesMutableLiveData:MutableLiveData<List<TMDBItem>> = MutableLiveData()
    
    init {
        getMediaLikes()
    }

    private fun getMediaLikes() {
        FirebaseDBRepository.getMediaLikes(mutableLikesMutableLiveData)
    }

}