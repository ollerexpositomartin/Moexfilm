package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.example.moexfilm.repositories.FirebaseDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibrariesMenuViewModel: ViewModel() {

    val librariesMutableLiveData = MutableLiveData<MutableList<Library>>()

    init {
        getLibraries()
    }


    fun getLibraries(){
        viewModelScope.launch(Dispatchers.IO) {FirebaseDBRepository.setListenerLibraries(librariesMutableLiveData)}
    }

    fun removeLibrary(library: Library){
        viewModelScope.launch(Dispatchers.IO) {FirebaseDBRepository.removeLibrary(library,object :FirebaseDBCallBack{
            override fun onSuccess(item: Any) {
                getLibraries()
            }
            override fun onFailure() {
                getLibraries()
            }
        })}
    }



}