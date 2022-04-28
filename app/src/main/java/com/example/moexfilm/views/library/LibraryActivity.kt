package com.example.moexfilm.views.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.databinding.ActivityLibraryBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.viewModels.LibraryViewModel
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter

class LibraryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLibraryBinding
    lateinit var adapter: LibraryItemsAdapter
    lateinit var libraryViewModel: LibraryViewModel
    lateinit var library:Library

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        libraryViewModel = LibraryViewModel(library)
        setRecycler()
        initObserverItems()
    }

    private fun initObserverItems() {
        libraryViewModel.itemsMutableLiveData.observe(this){ items ->
            adapter.submitList(items)
        }
    }

    private fun getData() {
        val data = intent.extras
        library = data?.getSerializable("LIBRARY")!! as Library
    }

    private fun setRecycler() {
        adapter = LibraryItemsAdapter { onItemTouch(it) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this,3)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun onItemTouch(tmdbItem: TMDBItem){

        if(tmdbItem is Movie){
            Log.d("MOVIE","MOVIE")
        }
        if(tmdbItem is TvShow){
            Log.d("TVSHOW","TVSHOW")
        }

    }
}