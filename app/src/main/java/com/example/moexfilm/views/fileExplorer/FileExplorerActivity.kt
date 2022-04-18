package com.example.moexfilm.views.fileExplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityFileExplorerBinding
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.util.visible
import com.example.moexfilm.viewModels.FileExplorerViewModel
import com.example.moexfilm.views.fileExplorer.adapters.FileExplorerAdapter

class FileExplorerActivity : AppCompatActivity() {
    lateinit var binding:ActivityFileExplorerBinding
    val fileExplorerViewModel:FileExplorerViewModel by viewModels()
    lateinit var authCode:String
    lateinit var idToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileExplorerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTokenObserver()
        initFoldersObserver()
        getData()
        setRecycler()
        fileExplorerViewModel.getTokens(authCode,idToken)
    }

    private fun setRecycler() {
        binding.recyclerView.adapter = FileExplorerAdapter{item ->
            onFolderTouch(item)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this,4)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun getData() {
        val data = intent.extras
        authCode = data?.getString("AUTHCODE","")!!
        idToken = data.getString("IDTOKEN","")!!
    }

    private fun initFoldersObserver() {
        fileExplorerViewModel.foldersMutableLiveData.observe(this){folders ->
            (binding.recyclerView.adapter as FileExplorerAdapter).submitList(folders)
        }
    }

    private fun initTokenObserver() {
        fileExplorerViewModel.tokenReceivedLiveData.observe(this){isTokenReceived ->
            if(isTokenReceived)
                Handler().postDelayed({binding.loadingView.visible},1500)
            else {
                Toast.makeText(this, getString(R.string.baseErrorRetry_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    fun onFolderTouch(item:GDriveElement) {
        fileExplorerViewModel.getChildFolders(item)
    }


}

