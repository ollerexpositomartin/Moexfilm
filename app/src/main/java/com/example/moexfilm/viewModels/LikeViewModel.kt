package com.example.moexfilm.viewModels

import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class LikeViewModel: ViewModel() {

    fun likeMedia(media:TMDBItem) {
        FirebaseDBRepository.likeMedia(media)
    }
}