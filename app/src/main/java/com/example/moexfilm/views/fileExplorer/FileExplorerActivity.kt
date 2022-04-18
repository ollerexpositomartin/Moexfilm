package com.example.moexfilm.views.fileExplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityFileExplorerBinding
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.util.changeVisibility
import com.example.moexfilm.viewModels.FileExplorerViewModel
import com.example.moexfilm.views.fileExplorer.adapters.FileExplorerAdapter

class FileExplorerActivity : AppCompatActivity() {
    lateinit var binding:ActivityFileExplorerBinding
    val fileExplorerViewModel:FileExplorerViewModel by viewModels()
    lateinit var adapter: FileExplorerAdapter
    lateinit var authCode:String
    lateinit var idToken:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileExplorerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        getData()
        setRecycler()
        setListener()
        fileExplorerViewModel.getTokens(authCode,idToken)
    }

    private fun initObservers() {
        initTokenObserver()
        initFoldersObserver()
        initRouteObserver()
    }

    private fun setListener() {
        binding.btnBackFolder.setOnClickListener { fileExplorerViewModel.getBackChildFolders();binding.loadingView.changeVisibility }
    }

    override fun onBackPressed() {fileExplorerViewModel.getBackChildFolders();binding.loadingView.changeVisibility }

    private fun setRecycler() {
        adapter = FileExplorerAdapter{item ->
            onFolderTouch(item)
        }
        binding.recyclerView.adapter = adapter
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
            adapter.submitList(folders)
            binding.loadingView.changeVisibility
        }
    }

    private fun initRouteObserver() {
        fileExplorerViewModel.routeFoldersMutableLiveData.observe(this){ route ->
            if(route.size>=2)
                binding.btnSelect.visibility = View.VISIBLE
            if(route.size < 2)
                binding.btnSelect.visibility = View.INVISIBLE
            if(route.size == 0) {
                binding.loadingView.changeVisibility
                finish()
            }
        }
    }

    private fun initTokenObserver() {
        fileExplorerViewModel.tokenReceivedLiveData.observe(this){isTokenReceived ->
            if(!isTokenReceived) {
                Toast.makeText(this, getString(R.string.baseErrorRetry_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun onFolderTouch(item:GDriveElement) {
        binding.loadingView.changeVisibility
        fileExplorerViewModel.getChildFolders(item)
    }

}

