package com.example.moexfilm.views.fileExplorer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.application.changeVisibility
import com.example.moexfilm.databinding.ActivityFileExplorerBinding
import com.example.moexfilm.models.data.ComplexGDriveElement
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.viewModels.FileExplorerViewModel
import com.example.moexfilm.views.fileExplorer.adapters.FileExplorerAdapter
import java.lang.StringBuilder

class FileExplorerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFileExplorerBinding
    private val fileExplorerViewModel:FileExplorerViewModel by viewModels()
    private lateinit var adapter: FileExplorerAdapter
    private lateinit var authCode:String
    private lateinit var idToken:String
    private lateinit var childsFolder:List<GDriveElement>
    private lateinit var route: List<GDriveElement>

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
        binding.btnSelect.setOnClickListener { returnFolderSelected() }
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
            childsFolder = folders
            adapter.submitList(folders)
            binding.loadingView.changeVisibility
        }
    }

    private fun getRouteString():String{
        val sb = StringBuilder()
       for ( i in route.indices){
           if(i == 0)
               sb.append(route[i].name)
           else
               sb.append("${route[i].name}/")
       }
        return sb.toString()
    }

    private fun initRouteObserver() {
        fileExplorerViewModel.routeFoldersMutableLiveData.observe(this){ route ->
            this.route = route
            binding.tvRoute.text = getRouteString()
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

    private fun returnFolderSelected() {
        val resultIntent = Intent()
        resultIntent.apply {
            putExtra("SELECTEDFOLDER",ComplexGDriveElement(route[route.size-1],childsFolder))
            putExtra("ROUTE",getRouteString())
        }
        setResult(RESULT_OK,resultIntent)
        finish()
    }

}

