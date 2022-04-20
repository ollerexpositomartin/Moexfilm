package com.example.moexfilm.views.main.fragments.librariesMenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moexfilm.databinding.FragmentLibrariesMenuBinding
import com.example.moexfilm.viewModels.LibrariesMenuViewModel
import com.example.moexfilm.views.CreateLibraryActivity

class LibrariesMenuFragment : Fragment() {
    private lateinit var binding: FragmentLibrariesMenuBinding
    private val librariesMenuViewModel: LibrariesMenuViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLibrariesMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener { startActivity(Intent(requireContext(),CreateLibraryActivity::class.java)) }
    }

}