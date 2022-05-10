package com.example.moexfilm.views.main.fragments.homeFragment.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.application.Application
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ItemMediaInprogressLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Episode
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.interfaces.Playable
import com.example.moexfilm.util.MediaUtil

class MediaInProgressAdapter(val onItemTouchListener:(TMDBItem)->Unit): ListAdapter<TMDBItem, MediaInProgressAdapter.ViewHolder>(DiffUtilCallBack) {
    lateinit var context: Context

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val binding = ItemMediaInprogressLayoutBinding.bind(v)
        init {
            binding.card.setOnClickListener { onItemTouchListener(getItem(adapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaInProgressAdapter.ViewHolder {
        context = parent.context
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_media_inprogress_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:MediaInProgressAdapter.ViewHolder, position: Int) {
        val media = getItem(position)
        val playableInfo:Playable = media as Playable

        if(media is Movie){
            Log.d("MediaInProgressAdapter","Movie")
            holder.binding.tvMediaName.text = media.name
            holder.binding.imvMedia.loadImage(Application.TMDB_IMAGE_URL.format(media.poster_path))
        }

        if(media is Episode){
            Log.d("MediaInProgressAdapter","Episode")
            holder.binding.tvMediaName.text = MediaUtil.createNameEpisodeInProgress(media)
            holder.binding.imvMedia.loadImage(Application.TMDB_IMAGE_URL.format(media.seasonPosterPath))
        }

        holder.binding.progressBar.max = playableInfo.duration().toInt()
        holder.binding.progressBar.progress = playableInfo.playedTime().toInt()

    }
}

private object DiffUtilCallBack: DiffUtil.ItemCallback<TMDBItem>(){
    override fun areItemsTheSame(oldItem:TMDBItem, newItem:TMDBItem): Boolean = oldItem.idDrive == newItem.idDrive

    override fun areContentsTheSame(oldItem:TMDBItem, newItem:TMDBItem): Boolean {
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
