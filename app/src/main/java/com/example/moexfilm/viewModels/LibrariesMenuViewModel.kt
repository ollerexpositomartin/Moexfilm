package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.repositories.FirebaseDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibrariesMenuViewModel: ViewModel() {

    val librariesMutableLiveData = MutableLiveData<List<Library>>()

    init {
        getLibraries()
    }


    fun getLibraries(){
        viewModelScope.launch(Dispatchers.IO) {FirebaseDBRepository.getLibraries(librariesMutableLiveData)}
    }

}