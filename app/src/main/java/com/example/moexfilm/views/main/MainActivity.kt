package com.example.moexfilm.views.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.moexfilm.R
import com.example.moexfilm.application.Prefs
import com.example.moexfilm.databinding.ActivityMainBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.util.StringUtil
import com.example.moexfilm.views.ScanActivity
import com.example.moexfilm.views.main.fragments.FavoriteFragment
import com.example.moexfilm.views.main.fragments.ProfileFragment
import com.example.moexfilm.views.main.fragments.SearchFragment
import com.example.moexfilm.views.main.fragments.homeFragment.HomeFragment
import com.example.moexfilm.views.main.fragments.librariesMenu.LibrariesMenuFragment


class MainActivity:ScanActivity() {
    private lateinit var binding:ActivityMainBinding

    val responseLibraryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ response ->
        if(response.resultCode == RESULT_OK){
            val data = response.data!!.extras
            library = data!!.getSerializable("LIBRARY") as Library
            initService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.favorite ->{
                    loadFragment(FavoriteFragment())
                    true
                }

                R.id.search -> {
                    loadFragment(SearchFragment())
                    true
                }

                R.id.libraries -> {
                    loadFragment(LibrariesMenuFragment())
                    true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment())
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
            scanning = true
        }else{
            service!!.onDestroy()
            binding.tvScanningLibrary.isSelected = false
            binding.scanningNotification.visibility = View.INVISIBLE
            scanning = false
        }
    }
}