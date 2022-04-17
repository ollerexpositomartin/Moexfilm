package com.example.moexfilm.views.fileExplorer.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.databinding.ItemFolderLayoutBinding
import com.example.moexfilm.models.data.GDriveElement
import kotlin.properties.Delegates

class FileExplorerAdapter(private val listener:(GDriveElement)->Unit): RecyclerView.Adapter<FileExplorerAdapter.ViewHolder>() {

    var folders: List<GDriveElement> by Delegates.observable(emptyList()){_,old,new->
        DiffUtil.calculateDiff(object :DiffUtil.Callback(){
            override fun getOldListSize(): Int = old.size
            override fun getNewListSize(): Int = new.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = old[oldItemPosition]
                val newItem = new[newItemPosition]

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return old[oldItemPosition] == new[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }

    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val binding = ItemFolderLayoutBinding.bind(v)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileExplorerAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FileExplorerAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

