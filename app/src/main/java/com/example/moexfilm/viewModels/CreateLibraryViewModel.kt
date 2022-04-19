package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.Movie
import com.example.moexfilm.models.repository.FirebaseDBRepository
import kotlinx.coroutines.launch

class CreateLibraryViewModel: ViewModel() {

    val libraryCreatedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun createLibrary( refreshToken:String, id:String, name:String, content:List<Movie>, type:String, language:String){
        viewModelScope.launch {
            val library:Library = Library(refreshToken,id,name,content,type,language)
            libraryCreatedLiveData.postValue(FirebaseDBRepository.createLibrary(library))
        }
    }
}