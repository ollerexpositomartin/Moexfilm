package com.example.moexfilm.views.main.fragments.librariesMenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.FragmentLibrariesMenuBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.interfaces.listeners.LibrariesMenuListener
import com.example.moexfilm.viewModels.LibrariesMenuViewModel
import com.example.moexfilm.views.CreateLibraryActivity
import com.example.moexfilm.views.library.LibraryActivity
import com.example.moexfilm.views.main.MainActivity
import com.example.moexfilm.views.main.fragments.librariesMenu.adapter.LibrariesMenuAdapter
import com.example.moexfilm.views.main.fragments.librariesMenu.adapter.SwipeGesture

class LibrariesMenuFragment : Fragment(),LibrariesMenuListener {
    private lateinit var binding: FragmentLibrariesMenuBinding
    private lateinit var adapter: LibrariesMenuAdapter
    private val libraryMenuViewModel:LibrariesMenuViewModel by viewModels()
    private var libraries = mutableListOf<Library>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLibrariesMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserverLibraries()
        binding.btnAdd.setOnClickListener { (activity as MainActivity).responseLibraryLauncher.launch(Intent(requireContext(), CreateLibraryActivity::class.java)) }
        adapter = LibrariesMenuAdapter(this)
        setRecycler()
    }

    override fun onResume() {
        super.onResume()
        libraryMenuViewModel.getLibraries()
    }

    private fun initObserverLibraries() {
        libraryMenuViewModel.librariesMutableLiveData.observe(viewLifecycleOwner){ libraries ->
                this.libraries = libraries
                adapter.submitList(libraries)
        }
    }


    private fun setRecycler() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onLibraryClick(library: Library) {
        startActivity(Intent(requireContext(),LibraryActivity::class.java).apply {
            putExtra("LIBRARY",library)
        })
    }

    override fun onMenuClick(positionMenu:Int) {
        val poupMenu = PopupMenu(requireContext(), binding.recyclerView[positionMenu].findViewById(R.id.btnMenuPopUp))
        poupMenu.inflate(R.menu.options_library_menu)

        poupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    libraryMenuViewModel.removeLibrary(libraries[positionMenu])
                    true
                }
                else -> false

            }
        }
        poupMenu.show()
    }

}