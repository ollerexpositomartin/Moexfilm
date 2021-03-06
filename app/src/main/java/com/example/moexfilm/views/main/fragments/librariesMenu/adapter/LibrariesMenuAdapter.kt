package com.example.moexfilm.views.main.fragments.librariesMenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ItemMenuLibraryLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Library
import com.example.moexfilm.models.interfaces.listeners.LibrariesMenuListener


class LibrariesMenuAdapter(val onLibrariesMenuListener: LibrariesMenuListener): ListAdapter<Library, LibrariesMenuAdapter.ViewHolder>(DiffUtilCallBack) {

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val binding = ItemMenuLibraryLayoutBinding.bind(v)
        init {
            binding.root.setOnClickListener { onLibrariesMenuListener.onLibraryClick(getItem(adapterPosition)) }
            binding.btnMenuPopUp.setOnClickListener { onLibrariesMenuListener.onMenuClick(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrariesMenuAdapter.ViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = layoutInflater.inflate(R.layout.item_menu_library_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibrariesMenuAdapter.ViewHolder, position: Int) {
        val library = getItem(position)
        holder.binding.tvLibraryName.text = library.name
    }

}


private object DiffUtilCallBack: DiffUtil.ItemCallback<Library>(){
    override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean  {
        var isSame = true

        if(oldItem.language != newItem.language)
            isSame = false
        if(oldItem.name != newItem.name)
            isSame = false
        if(oldItem.owner != newItem.owner)
            isSame = false
        if(oldItem.type != newItem.type)
            isSame = false

        return isSame
    }
}