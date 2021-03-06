package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.repositories.FirebaseDBRepository

class SearchViewModel:ViewModel() {

    val mutableSearchedsMutableLiveData:MutableLiveData<List<TMDBItem>> = MutableLiveData()

    fun searchTMDBItems(query:String){
            FirebaseDBRepository.searchTMDBItems(query, mutableSearchedsMutableLiveData)
    }


}