package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.Movie
import com.example.moexfilm.models.interfaces.callBacks.FirebaseDBCallBack
import com.example.moexfilm.models.repository.FirebaseDBRepository
import kotlinx.coroutines.launch

class CreateLibraryViewModel: ViewModel() {

    val libraryCreatedLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun createLibrary( accountId:String, id:String, name:String, content:List<Movie>, type:String, language:String){
        viewModelScope.launch {
            val library:Library = Library(accountId,id,name,content,type,language)
            FirebaseDBRepository.createLibrary(library,object :FirebaseDBCallBack{
                override fun onSuccess() {
                    libraryCreatedLiveData.postValue(true)
                }
                override fun onFailure() {
                    libraryCreatedLiveData.postValue(false)
                }
            })
        }
    }
}