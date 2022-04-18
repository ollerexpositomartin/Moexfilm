package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.models.interfaces.listeners.TokenCallBack
import com.example.moexfilm.models.repository.GDriveRepository
import com.example.moexfilm.models.repository.TokenRepository
import kotlinx.coroutines.launch

class FileExplorerViewModel:ViewModel() {
    val tokenReceivedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val foldersMutableLiveData:MutableLiveData<MutableList<GDriveElement>> = MutableLiveData()
    val routeFolders:MutableList<GDriveElement> = mutableListOf()

    init {
        loadInitFolders()
    }

    private fun loadInitFolders() {
        val myUnit = GDriveElement("My Unit","root")
        val sharedWithMe = GDriveElement("Shared With Me","drives")
        val list = mutableListOf<GDriveElement>(myUnit,sharedWithMe)
        foldersMutableLiveData.postValue(list)
    }

    fun getTokens(authCode:String, idToken:String){
        viewModelScope.launch {
            TokenRepository.getTokens(authCode,idToken,object: TokenCallBack {
                override fun onSucess() {
                    tokenReceivedLiveData.postValue(true)
                }
                override fun onFailure() {
                    tokenReceivedLiveData.postValue(false)
                }
            })
        }
    }

    fun getChildFolders(item:GDriveElement){
        viewModelScope.launch {
            GDriveRepository.getChildFolders(item)
        }
    }

}