package com.example.moexfilm.views.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityLibraryBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.viewModels.LibraryViewModel
import com.example.moexfilm.views.detailsMovie.DetailsMovieActivity
import com.example.moexfilm.views.ScanActivity
import com.example.moexfilm.views.DetailsTvShowActivity
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter

class LibraryActivity :ScanActivity() {
    private lateinit var binding:ActivityLibraryBinding
    lateinit var adapter: LibraryItemsAdapter
    lateinit var libraryViewModel: LibraryViewModel

    //AÑADIR ACCESSTOKEN ADQUISICION POR LO DEMAS FUNCIONATODO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        libraryViewModel = LibraryViewModel(library)
        setRecycler()
        setListeners()
        initObserverItems()
    }


    private fun setListeners() {
        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getColor(R.color.background_moexfilm))
        binding.swipeRefreshLayout.setColorSchemeColors(getColor(R.color.accent_moexfilm))
        binding.swipeRefreshLayout.setOnRefreshListener { initService() }
    }

    override fun isRunning(libraryItemList: MutableList<Library>) {
        if(!libraryItemList.contains(library))
            binding.swipeRefreshLayout.isRefreshing = false
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
            startActivity(Intent(this, DetailsMovieActivity::class.java).apply {
                putExtra("MOVIE", tmdbItem)
                putExtra("LANGUAGE", library.language)
            })
        }

        if(tmdbItem is TvShow){
            startActivity(Intent(this, DetailsTvShowActivity::class.java).apply {
                putExtra("TVSHOW", tmdbItem)
            })
        }
    }
}