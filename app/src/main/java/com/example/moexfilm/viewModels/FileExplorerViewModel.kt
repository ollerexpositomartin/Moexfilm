package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.data.mediaObjects.GDriveItem
import com.example.moexfilm.models.interfaces.callBacks.GDriveCallBack
import com.example.moexfilm.models.interfaces.callBacks.TokenCallBack
import com.example.moexfilm.repositories.GDriveRepository
import com.example.moexfilm.repositories.TokenRepository
import kotlinx.coroutines.launch

class FileExplorerViewModel:ViewModel() {
    val tokenReceivedLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val foldersMutableLiveData:MutableLiveData<ArrayList<GDriveItem>?> = MutableLiveData()
    val routeFoldersMutableLiveData:MutableLiveData<ArrayList<GDriveItem>> = MutableLiveData()

    private val queryChildFolders = "'%s' in parents and mimeType = 'application/vnd.google-apps.folder'"

    init {
        loadInitFolders()
    }

    private fun loadInitFolders() {
        val myUnit = GDriveItem("My Unit","root")
        val sharedWithMe = GDriveItem("Shared With Me","drives")
        val list = arrayListOf<GDriveItem>(myUnit,sharedWithMe)
        foldersMutableLiveData.postValue(list)
        routeFoldersMutableLiveData.postValue(arrayListOf(GDriveItem("/","/")))
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
                routeFolders.size > 2 && routeFolders[routeFolders.size-2].idDrive != "drives" -> {
                    routeFolders.removeAt(routeFolders.size - 1)
                    val item: GDriveItem = routeFolders[routeFolders.size - 1]
                    GDriveRepository.getChildItems(queryChildFolders.format(item.idDrive),object :GDriveCallBack{
                        override fun onSuccess(response: ArrayList<GDriveItem>?) {
                            foldersMutableLiveData.postValue(response)
                            routeFoldersMutableLiveData.postValue(routeFolders)
                        }
                        override fun onFailure() {
                            tokenReceivedLiveData.postValue(false)
                        }
                    })
                }
                routeFolders.size > 2 && routeFolders[routeFolders.size-2].idDrive == "drives" -> {
                    routeFolders.removeAt(routeFolders.size - 1)
                    GDriveRepository.getTeamDrives(object :GDriveCallBack{
                        override fun onSuccess(response: ArrayList<GDriveItem>?) {
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

    fun getChildFolders(item: GDriveItem){
        viewModelScope.launch {
            if(item.idDrive != "drives")
                GDriveRepository.getChildItems(queryChildFolders.format(item.idDrive), object : GDriveCallBack {
                    override fun onSuccess(response: ArrayList<GDriveItem>?) {
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
                    override fun onSuccess(response: ArrayList<GDriveItem>?) {
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