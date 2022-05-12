package com.example.moexfilm.views.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.application.capitalize
import com.example.moexfilm.databinding.FragmentSearchBinding
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.viewModels.SearchViewModel
import com.example.moexfilm.views.DetailsTvShowActivity
import com.example.moexfilm.views.detailsMovie.DetailsMovieActivity
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter

class SearchFragment : Fragment() {
    lateinit var binding:FragmentSearchBinding
    val searchViewModel:SearchViewModel by viewModels()
    lateinit var adapter:LibraryItemsAdapter
    var currentText:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setRecycler()
        initObserverSearcheds()
    }

    private fun setRecycler() {
        adapter = LibraryItemsAdapter { onItemSearchedClick(it) }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initObserverSearcheds() {
        searchViewModel.mutableSearchedsMutableLiveData.observe(viewLifecycleOwner){ TMDBItemsSearched ->
            if(TMDBItemsSearched.isNotEmpty())
                if(currentText.isNotEmpty())
                adapter.submitList(TMDBItemsSearched)
        }
    }

    private fun setListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {return false}


            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.submitList(emptyList())
                    currentText = p0.toString()
                    if(!p0.isNullOrEmpty()) {
                        searchViewModel.searchTMDBItems(p0.capitalize())
                        return true
                    }
                return false
            }
        })
    }

    private fun onItemSearchedClick(item:TMDBItem){

        if(item is Movie){
            startActivity(Intent(requireContext(),DetailsMovieActivity::class.java).apply {
                putExtra("MOVIE",item)
            })
        }

        if(item is TvShow) {
            startActivity(Intent(requireContext(), DetailsTvShowActivity::class.java).apply {
                putExtra("TVSHOW", item)
            })
        }
    }


}