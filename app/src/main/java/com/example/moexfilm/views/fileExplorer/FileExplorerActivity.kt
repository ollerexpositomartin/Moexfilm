package com.example.moexfilm.views.fileExplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.application.changeVisibility
import com.example.moexfilm.databinding.ActivityFileExplorerBinding
import com.example.moexfilm.models.data.mediaObjects.GDriveItem
import com.example.moexfilm.viewModels.FileExplorerViewModel
import com.example.moexfilm.views.fileExplorer.adapters.FileExplorerAdapter
import java.lang.StringBuilder

class FileExplorerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFileExplorerBinding
    private val fileExplorerViewModel:FileExplorerViewModel by viewModels()
    private lateinit var adapter: FileExplorerAdapter
    private lateinit var authCode:String
    private lateinit var idToken:String
    private lateinit var accountId:String
    private lateinit var route: List<GDriveItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileExplorerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        getData()
        setRecycler()
        setListener()
        fileExplorerViewModel.getTokens(accountId,authCode,idToken)
    }

    private fun initObservers() {
        initTokenObserver()
        initFoldersObserver()
        initRouteObserver()
    }

    private fun setListener() {
        binding.btnBackFolder.setOnClickListener { onBackPressed() }
        binding.btnSelect.setOnClickListener { returnFolderSelected() }
    }

    override fun onBackPressed() {
        //Podria cambiar esto para que directamente cuando la ruta no corresponde con el parent de la carpeta cargada no los cargue
        // lo pongo para que no se pueda ir hacia atras mientras se estan cargando datos
        if(binding.loadingIndicator.isInvisible) {
            binding.loadingIndicator.changeVisibility
            adapter.submitList(emptyList())
            fileExplorerViewModel.getBackChildFolders()
        }
    }

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
        idToken = data.getString("IDTOKEN","")
        accountId = data.getString("ACCOUNTID","")
    }

    private fun initFoldersObserver() {
        fileExplorerViewModel.foldersMutableLiveData.observe(this){folders ->
            adapter.submitList(folders)
            binding.loadingIndicator.changeVisibility
        }
    }

    private fun getRouteString():String{
        val sb = StringBuilder()
       for ( i in route.indices){
           if(i == 0)
               sb.append(route[i].fileName)
           else
               sb.append("${route[i].fileName}/")
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
                binding.loadingIndicator.changeVisibility
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

    private fun onFolderTouch(item: GDriveItem) {
        binding.loadingIndicator.changeVisibility
        adapter.submitList(emptyList())
        fileExplorerViewModel.getChildFolders(item)
    }

    private fun returnFolderSelected() {
        val resultIntent = Intent()
        resultIntent.apply {
            putExtra("SELECTEDFOLDER",route[route.size-1])
            putExtra("ROUTE",getRouteString())
        }
        setResult(RESULT_OK,resultIntent)
        finish()
    }

}

