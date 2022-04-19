package com.example.moexfilm.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityMainBinding
import com.example.moexfilm.views.main.fragments.HomeFragment
import com.example.moexfilm.views.main.fragments.LibrariesMenuFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

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