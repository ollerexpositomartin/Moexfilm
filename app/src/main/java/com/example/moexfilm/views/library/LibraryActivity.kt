package com.example.moexfilm.views.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ActivityLibraryBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.viewModels.LibraryViewModel
import com.example.moexfilm.views.DetailsMovieActivity
import com.example.moexfilm.views.ScanActivity
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter
import java.io.Serializable

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
        binding.swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                initService()
            }
        })
    }

    override fun isRunning(libraryItemList: MutableList<Library>) {
        if(libraryItemList.isEmpty())
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
            startActivity(Intent(this,DetailsMovieActivity::class.java).apply {
                putExtra("MOVIE", tmdbItem)
            })

        }
        if(tmdbItem is TvShow){
            Log.d("TVSHOW","TVSHOW")
        }

    }
}