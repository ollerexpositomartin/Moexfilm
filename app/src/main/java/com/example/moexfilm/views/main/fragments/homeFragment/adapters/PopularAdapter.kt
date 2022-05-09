package com.example.moexfilm.views.main.fragments.homeFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.application.Application
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ItemMediaLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem

class PopularAdapter(val onItemTouchListener:(Movie)->Unit): ListAdapter<Movie, PopularAdapter.ViewHolder>(DiffUtilCallBack) {
    lateinit var context: Context

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val binding = ItemMediaLayoutBinding.bind(v)
        init {
            binding.card.setOnClickListener { onItemTouchListener(getItem(adapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        context = parent.context
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_media_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:PopularAdapter.ViewHolder, position: Int) {
        val media = getItem(position)
        holder.binding.tvMediaName.text = media.name
        holder.binding.imvMedia.loadImage(Application.TMDB_IMAGE_URL.format(media.poster_path))
    }
}

private object DiffUtilCallBack: DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.idDrive == newItem.idDrive

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        var isSame = true

        if(oldItem.backdrop_path != newItem.backdrop_path)
            isSame = false
        if(oldItem.fileName != newItem.fileName)
            isSame = false
        if(oldItem.name != newItem.name)
            isSame = false
        if(oldItem.overview != newItem.overview)
            isSame = false
        if(oldItem.parentFolder != newItem.parentFolder)
            isSame = false
        if(oldItem.popularity != newItem.popularity)
            isSame = false
        if(oldItem.poster_path != newItem.poster_path)
            isSame = false
        if(oldItem.vote_average != newItem.vote_average)
            isSame = false

        return isSame
    }
}
