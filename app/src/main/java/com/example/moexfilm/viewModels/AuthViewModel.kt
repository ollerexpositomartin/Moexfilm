package com.example.moexfilm.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moexfilm.models.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    val authenticatedUserLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val registeredUserLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun signinWithGoogle(googleAuthCredential: AuthCredential) {
        viewModelScope.launch {
            val isAuthenticated = AuthRepository().firebaseSigninWithGoogle(googleAuthCredential)
            authenticatedUserLiveData.postValue(isAuthenticated)
        }
    }

    fun registerEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            val isRegistered =
                AuthRepository().firebaseCreateUserWithEmailAndPassword(email, password)
            registeredUserLiveData.postValue(isRegistered)
        }
    }

    fun signinEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            val isAuthenticated = AuthRepository().firebaseSigninEmailPassword(email, password)
            authenticatedUserLiveData.postValue(isAuthenticated)
        }
    }

}