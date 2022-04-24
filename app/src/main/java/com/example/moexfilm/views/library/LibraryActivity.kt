package com.example.moexfilm.views.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.databinding.ActivityLibraryBinding
import com.example.moexfilm.models.data.Movie
import com.example.moexfilm.viewModels.LibraryViewModel
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter

class LibraryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLibraryBinding
    lateinit var adapter: LibraryItemsAdapter
    lateinit var libraryViewModel: LibraryViewModel
    lateinit var libraryId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        libraryViewModel = LibraryViewModel(libraryId)
        setRecycler()
        initObserverItems()
    }

    private fun initObserverItems() {
        libraryViewModel.itemsMutableLiveData.observe(this){ movies ->
            adapter.submitList(movies)
        }
    }

    private fun getData() {
        val data = intent.extras
        libraryId = data?.getString("LIBRARYID")?:""
    }

    private fun setRecycler() {
        adapter = LibraryItemsAdapter { onItemTouch(it) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this,3)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun onItemTouch(movie:Movie){

    }
}