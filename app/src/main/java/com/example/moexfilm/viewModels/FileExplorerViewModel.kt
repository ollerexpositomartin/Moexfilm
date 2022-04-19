package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.example.moexfilm.models.repository.GDriveRepository
import com.example.moexfilm.models.repository.TokenRepository
import kotlinx.coroutines.launch

class FileExplorerViewModel:ViewModel() {
    val tokenReceivedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val foldersMutableLiveData:MutableLiveData<ArrayList<GDriveElement>> = MutableLiveData()
    val routeFoldersMutableLiveData:MutableLiveData<ArrayList<GDriveElement>> = MutableLiveData()

    init {
        loadInitFolders()
    }

    private fun loadInitFolders() {
        val myUnit = GDriveElement("My Unit","root")
        val sharedWithMe = GDriveElement("Shared With Me","drives")
        val list = arrayListOf<GDriveElement>(myUnit,sharedWithMe)
        foldersMutableLiveData.postValue(list)
        routeFoldersMutableLiveData.postValue(arrayListOf(GDriveElement("/","/")))
    }

    fun getTokens(accountId:String,authCode:String, idToken:String){
        viewModelScope.launch {
            TokenRepository.getTokens(accountId,authCode,idToken,object: TokenCallBack {
                override fun onSucess() {
                    tokenReceivedLiveData.postValue(true)
                }
                override fun onFailure() {
                    tokenReceivedLiveData.postValue(false)
                }
            })
        }
    }

    fun getBackChildFolders(){
        viewModelScope.launch {
            val routeFolders = routeFoldersMutableLiveData.value!!
            when {
                routeFolders.size > 2 && routeFolders[routeFolders.size-2].id != "drives" -> {
                    routeFolders.removeAt(routeFolders.size - 1)
                    GDriveRepository.getChildFolders(routeFolders[routeFolders.size - 1],object :GDriveCallBack{
                        override fun onSuccess(response: ArrayList<GDriveElement>) {
                            foldersMutableLiveData.postValue(response)
                            routeFoldersMutableLiveData.postValue(routeFolders)
                        }
                        override fun onFailure() {
                            tokenReceivedLiveData.postValue(false)
                        }
                    })
                }
                routeFolders.size > 2 && routeFolders[routeFolders.size-2].id == "drives" -> {
                    routeFolders.removeAt(routeFolders.size - 1)
                    GDriveRepository.getTeamDrives(object :GDriveCallBack{
                        override fun onSuccess(response: ArrayList<GDriveElement>) {
                            foldersMutableLiveData.postValue(response)
                            routeFoldersMutableLiveData.postValue(routeFolders)
                        }
                        override fun onFailure() {
                            tokenReceivedLiveData.postValue(false)
                        }
                    })
                }
                routeFolders.size == 2 -> loadInitFolders()
                routeFolders.size == 1 -> routeFoldersMutableLiveData.postValue(arrayListOf())
            }
        }
    }

    fun getChildFolders(item:GDriveElement){
        viewModelScope.launch {
            if(item.id != "drives")
                GDriveRepository.getChildFolders(item, object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveElement>) {
                        routeFoldersMutableLiveData.value!!.add(item)
                        routeFoldersMutableLiveData.postValue(routeFoldersMutableLiveData.value)
                        foldersMutableLiveData.postValue(response)
                    }
                    override fun onFailure() {
                        tokenReceivedLiveData.postValue(false)
                    }
                })
            else
                GDriveRepository.getTeamDrives(object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveElement>) {
                        routeFoldersMutableLiveData.value!!.add(item)
                        routeFoldersMutableLiveData.postValue(routeFoldersMutableLiveData.value)
                        foldersMutableLiveData.postValue(response)
                    }
                    override fun onFailure() {
                        tokenReceivedLiveData.postValue(false)
                    }
                })



        }
    }

}