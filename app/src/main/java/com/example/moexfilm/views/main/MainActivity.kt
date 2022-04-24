package com.example.moexfilm.views.main

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moexfilm.R
import com.example.moexfilm.application.Application
import com.example.moexfilm.application.services.ScanLibraryService
import com.example.moexfilm.databinding.ActivityMainBinding
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.interfaces.listeners.ServiceListener
import com.example.moexfilm.util.StringUtil
import com.example.moexfilm.views.main.fragments.HomeFragment
import com.example.moexfilm.views.main.fragments.librariesMenu.LibrariesMenuFragment
import java.lang.StringBuilder


class MainActivity : AppCompatActivity(),ServiceListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var connection:ServiceConnection
    var service: ScanLibraryService? = null
    lateinit var library:Library

    val responseLibraryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ response ->
        if(response.resultCode == RESULT_OK){
            val data = response.data!!.extras
            library = data!!.getSerializable("LIBRARY") as Library
            initService()
        }
    }

    private fun initService() {
        if(service == null){
            val service = Intent(this, ScanLibraryService::class.java).also { service ->
                startService(service)
                bindService(service, connection, BIND_AUTO_CREATE)
            }
        }else {
            service!!.startScan(library)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connection = object:ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                service = (p1 as ScanLibraryService.LocalBinder).getService()
                service!!.setScanServiceListener(this@MainActivity)
                service!!.startScan(library)
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d("HOLA","HOLA")
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.favorite ->{
                    true
                }

                R.id.search -> {
                    true
                }

                R.id.libraries -> {
                    loadFragment(LibrariesMenuFragment())
                    true
                }

                R.id.profile -> {
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,fragment)
            .commit()
    }

    override fun isRunning(libraryItemList:MutableList<Library>) {
        if(libraryItemList.size>0) {
            binding.tvScanningLibrary.text = getString(R.string.scanningLibrary_text).format(StringUtil.scanItemListToText(libraryItemList))
            binding.tvScanningLibrary.isSelected = true
            binding.scanningNotification.visibility = View.VISIBLE
        }else{
            service!!.onDestroy()
            binding.tvScanningLibrary.isSelected = false
            binding.scanningNotification.visibility = View.INVISIBLE
        }
    }
}