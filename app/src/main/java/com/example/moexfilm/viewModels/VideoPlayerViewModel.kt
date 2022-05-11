package com.example.moexfilm.viewModels

import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class VideoPlayerViewModel: ViewModel() {

    fun saveMediaInProgress(media: TMDBItem){
        FirebaseDBRepository.saveMediaInProgress(media)
    }

    fun removeMediaInProgress(media:TMDBItem){
        FirebaseDBRepository.removeMediaInProgress(media)
    }

}