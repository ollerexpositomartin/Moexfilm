package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class HomeFragmentViewModel: ViewModel() {
    val mutableListRandomItemsMutableLiveData:MutableLiveData<MutableList<TMDBItem>> = MutableLiveData()

    init {
        loadRandomContent()
    }

    fun loadRandomContent(){
        FirebaseDBRepository.getRandomContent(mutableListRandomItemsMutableLiveData)
    }


}