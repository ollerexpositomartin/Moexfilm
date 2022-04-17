package com.example.moexfilm.views.fileExplorer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.ItemFolderLayoutBinding
import com.example.moexfilm.models.data.GDriveElement


class FileExplorerAdapter():ListAdapter<GDriveElement,FileExplorerAdapter.ViewHolder>(DiffUtilCallBack)  {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val binding = ItemFolderLayoutBinding.bind(v)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileExplorerAdapter.ViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view:View = layoutInflater.inflate(R.layout.item_folder_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileExplorerAdapter.ViewHolder, position: Int) {
        val gDriveElement = getItem(position)
        holder.binding.tvName.text = gDriveElement.name
    }
}

private object DiffUtilCallBack: DiffUtil.ItemCallback<GDriveElement>(){
    override fun areItemsTheSame(oldItem: GDriveElement, newItem: GDriveElement): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GDriveElement, newItem: GDriveElement): Boolean = oldItem == newItem
}

