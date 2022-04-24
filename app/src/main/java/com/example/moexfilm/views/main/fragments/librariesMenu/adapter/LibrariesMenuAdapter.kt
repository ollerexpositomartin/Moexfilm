package com.example.moexfilm.views.main.fragments.librariesMenu.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ItemFolderLayoutBinding
import com.example.moexfilm.databinding.ItemMenuLibraryLayoutBinding
import com.example.moexfilm.models.data.GDriveItem
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.Movie
import com.example.moexfilm.models.interfaces.listeners.FireBaseAdapterListener
import com.example.moexfilm.repositories.FirebaseDBRepository


class LibrariesMenuAdapter(val onFolderTouchListener:(Library)->Unit): ListAdapter<Library, LibrariesMenuAdapter.ViewHolder>(DiffUtilCallBack) {

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val binding = ItemMenuLibraryLayoutBinding.bind(v)
        init {
            binding.root.setOnClickListener { onFolderTouchListener(getItem(adapterPosition)) }
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