package com.example.moexfilm.views.main.fragments.librariesMenu.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ItemMenuLibraryLayoutBinding
import com.example.moexfilm.models.data.GDriveElement
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.interfaces.listeners.FireBaseAdapterListener
import com.example.moexfilm.models.repository.FirebaseDBRepository
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class LibrariesMenuAdapter(val fireBaseAdapterListener: FireBaseAdapterListener) :FirebaseRecyclerAdapter<Library,LibrariesMenuAdapter.ViewHolder>(
    FirebaseRecyclerOptions.Builder<Library>().apply {
        setQuery(FirebaseDBRepository.getLibraries(),Library::class.java)
        DiffUtilCallBack
    }.build()){

    inner class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val binding:ItemMenuLibraryLayoutBinding = ItemMenuLibraryLayoutBinding.bind(v)
        init {
            binding.root.setOnClickListener { fireBaseAdapterListener.onClickItemListener(getItem(adapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = layoutInflater.inflate(R.layout.item_menu_library_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        fireBaseAdapterListener.sizeListener(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Library) {
        val library = getItem(position)
        holder.binding.tvLibraryName.text = library.name
    }
}

private object DiffUtilCallBack: DiffUtil.ItemCallback<Library>(){
    override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean = oldItem == newItem
}