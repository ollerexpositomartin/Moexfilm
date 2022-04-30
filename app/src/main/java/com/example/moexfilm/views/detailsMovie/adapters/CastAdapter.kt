package com.example.moexfilm.views.detailsMovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ItemActorLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Cast

class CastAdapter(): ListAdapter<Cast,CastAdapter.ViewHolder>(DiffUtilCallBack)  {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val binding = ItemActorLayoutBinding.bind(v)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_actor_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) {
        val actor = getItem(position)
        holder.binding.tvActorName.text = actor.name
        holder.binding.tvCharacterName.text = actor.character
        holder.binding.imvActor.loadImage(TMDB_IMAGE_URL.format(actor.profilePath))
    }
}

private object DiffUtilCallBack: DiffUtil.ItemCallback<Cast>(){
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean = oldItem == newItem
}

