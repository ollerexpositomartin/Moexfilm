package com.example.moexfilm.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.repositories.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    val authenticatedUserLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val registeredUserLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun signinWithGoogle(googleAuthCredential: AuthCredential) {
        viewModelScope.launch(Dispatchers.IO) {
            val isAuthenticated = AuthRepository.firebaseSigninWithGoogle(googleAuthCredential)
            authenticatedUserLiveData.postValue(isAuthenticated)
        }
    }

    fun registerEmailPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered =
                AuthRepository.firebaseCreateUserWithEmailAndPassword(email, password)
            registeredUserLiveData.postValue(isRegistered)
        }
    }

    fun signinEmailPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isAuthenticated = AuthRepository.firebaseSigninEmailPassword(email, password)
            authenticatedUserLiveData.postValue(isAuthenticated)
        }
    }

}