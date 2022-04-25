package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.repositories.FirebaseDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryViewModel(id:String): ViewModel() {
    val itemsMutableLiveData:MutableLiveData<List<Movie>> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) { FirebaseDBRepository.setListenerItemsLibrary(id,itemsMutableLiveData)}
    }
}