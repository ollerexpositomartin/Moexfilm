package com.example.moexfilm.views.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moexfilm.R
import com.example.moexfilm.application.isServiceRunning
import com.example.moexfilm.application.services.ScanLibraryService
import com.example.moexfilm.databinding.ActivityMainBinding
import com.example.moexfilm.views.main.fragments.HomeFragment
import com.example.moexfilm.views.main.fragments.librariesMenu.LibrariesMenuFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    companion object{
        lateinit var connection:ServiceConnection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        connection = object:ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                (p1 as ScanLibraryService.LocalBinder).getService()
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
}