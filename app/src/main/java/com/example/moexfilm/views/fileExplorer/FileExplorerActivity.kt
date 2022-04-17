package com.example.moexfilm.views.fileExplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.moexfilm.R
import com.example.moexfilm.viewModels.FileExplorerViewModel

class FileExplorerActivity : AppCompatActivity() {
    val fileExplorerViewModel:FileExplorerViewModel by viewModels()
    lateinit var authCode:String
    lateinit var idToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_explorer)
        getData()
        fileExplorerViewModel.getTokens(authCode,idToken)
        initTokenObserver()
    }

    private fun getData() {
        val data = intent.extras
        authCode = data?.getString("AUTHCODE","")!!
        idToken = data.getString("IDTOKEN","")!!
    }

    private fun initTokenObserver() {
        fileExplorerViewModel.tokenReceivedLiveData.observe(this){isTokenReceived ->
            if(isTokenReceived)

            else {
                Toast.makeText(this, getString(R.string.baseErrorRetry_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}

