package com.example.moexfilm.views.main.fragments.librariesMenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moexfilm.databinding.FragmentLibrariesMenuBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.viewModels.LibrariesMenuViewModel
import com.example.moexfilm.views.CreateLibraryActivity
import com.example.moexfilm.views.library.LibraryActivity
import com.example.moexfilm.views.main.MainActivity
import com.example.moexfilm.views.main.fragments.librariesMenu.adapter.LibrariesMenuAdapter

class LibrariesMenuFragment : Fragment() {
    private lateinit var binding: FragmentLibrariesMenuBinding
    private lateinit var adapter: LibrariesMenuAdapter
    private val libraryMenuViewModel:LibrariesMenuViewModel by viewModels()
    private val lastSizeLibraries:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLibrariesMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserverLibraries()
        binding.btnAdd.setOnClickListener { (activity as MainActivity).responseLibraryLauncher.launch(Intent(requireContext(), CreateLibraryActivity::class.java)) }
        adapter = LibrariesMenuAdapter {
            onClickLibrary(it)
        }
        setRecycler()
    }

    override fun onResume() {
        super.onResume()
        libraryMenuViewModel.getLibraries()
    }

    private fun initObserverLibraries() {
        libraryMenuViewModel.librariesMutableLiveData.observe(viewLifecycleOwner){ libraries ->
                binding.recyclerView.scheduleLayoutAnimation()
                adapter.submitList(libraries)
        }
    }

    private fun onClickLibrary(library: Library) {
        startActivity(Intent(requireContext(),LibraryActivity::class.java).apply {
            putExtra("LIBRARY",library)
        })
    }

    private fun setRecycler() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}