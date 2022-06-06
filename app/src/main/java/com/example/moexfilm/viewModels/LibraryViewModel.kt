package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.example.moexfilm.repositories.FirebaseDBRepository
import com.example.moexfilm.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryViewModel(library:Library): ViewModel() {
    val itemsMutableLiveData:MutableLiveData<List<TMDBItem>> = MutableLiveData()
    val isAccessTokenObtained:MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) { FirebaseDBRepository.setListenerItemLibrary(library,itemsMutableLiveData)}
    }

    fun callTokens(libraryId:String){
        viewModelScope.launch(Dispatchers.IO) {
            TokenRepository.getTokens(libraryId, object : TokenCallBack {
                override fun onSucess() {
                    isAccessTokenObtained.postValue(true)
                }
                override fun onFailure() {isAccessTokenObtained.postValue(false)} })
        }
    }

}