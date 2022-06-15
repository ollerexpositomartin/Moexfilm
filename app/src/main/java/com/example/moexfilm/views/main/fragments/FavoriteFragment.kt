package com.example.moexfilm.views.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moexfilm.R
import com.example.moexfilm.databinding.FragmentFavoriteBinding
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.data.mediaObjects.TvShow
import com.example.moexfilm.viewModels.FavoriteViewModel
import com.example.moexfilm.views.DetailsTvShowActivity
import com.example.moexfilm.views.detailsMovie.DetailsMovieActivity
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter


class FavoriteFragment : Fragment() {
    lateinit var binding:FragmentFavoriteBinding
    private val favoriteViewModel:FavoriteViewModel by viewModels()
    private lateinit var adapter:LibraryItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        initObserverMediaFavorite()
    }

    private fun setRecyclerView() {
        adapter = LibraryItemsAdapter(){onFavoriteItemClick(it)}
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun onFavoriteItemClick(item: TMDBItem){
        if(item is Movie){
            val movie:Movie = item
            startActivity(Intent(requireContext(),DetailsMovieActivity::class.java).apply {
                putExtra("MOVIE", movie)
            })
            }

        if(item is TvShow){
            val tvShow:TvShow = item
            startActivity(Intent(requireContext(),DetailsTvShowActivity::class.java).putExtra("TVSHOW",tvShow))
        }
    }

    private fun initObserverMediaFavorite() {
        favoriteViewModel.mutableLikesMutableLiveData.observe(viewLifecycleOwner) { favorites ->
             adapter.submitList(favorites)
        }
    }
}