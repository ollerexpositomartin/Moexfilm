package com.example.moexfilm.views

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.CLIENT_ID
import com.example.moexfilm.viewModels.AuthViewModel
import com.example.moexfilm.views.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

abstract class AuthActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val authViewModel: AuthViewModel by viewModels()

    private var responseGoogleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { response ->
            if (response.resultCode == RESULT_OK) {
                val data = response.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null)
                        getGoogleAuthCredential(account)
                } catch (e: ApiException) {
                }
            }
        }

    fun initSignInObserver(){
        authViewModel.authenticatedUserLiveData.observe(this) { authenticatedUser ->
            if (authenticatedUser) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else Toast.makeText(this, getString(R.string.signIn_error), Toast.LENGTH_LONG).show()
        }
    }

    fun initRegisteredObserver(){
        authViewModel.registeredUserLiveData.observe(this) { authenticatedUser ->
            if (authenticatedUser) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finishAffinity()
            } else
                Toast.makeText(this, getString(R.string.register_error), Toast.LENGTH_LONG)
                    .show()
        }
    }

    fun initGoogleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        responseGoogleSignIn.launch(signInIntent)
        googleSignInClient.signOut()
    }

    private fun getGoogleAuthCredential(account: GoogleSignInAccount) {
        val googleTokenId: String = account.idToken!!
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        authViewModel.signinWithGoogle(googleAuthCredential)
    }

    fun signinWithEmailPassword(email:String, password:String){
        authViewModel.signinEmailPassword(email,password)
    }

    fun registerWithEmailPassword(email: String, password: String) = authViewModel.registerEmailPassword(email, password)

}