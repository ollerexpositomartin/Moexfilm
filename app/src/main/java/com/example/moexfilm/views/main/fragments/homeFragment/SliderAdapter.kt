package com.example.moexfilm.views.main.fragments.homeFragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.TMDB_IMAGE_URL
import com.example.moexfilm.application.loadImage
import com.example.moexfilm.databinding.ItemSliderLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.smarteist.autoimageslider.SliderViewAdapter


public class SliderAdapter(val onItemclick:(TMDBItem) -> Unit) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mSliderItems: MutableList<TMDBItem> = ArrayList()

    fun renewItems(sliderItems: MutableList<TMDBItem>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem:TMDBItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_slider_layout, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: TMDBItem = mSliderItems[position]
        viewHolder.binding.tvNameSlider.text = sliderItem.name
        viewHolder.binding.imvSlider.loadImage(TMDB_IMAGE_URL.format(sliderItem.backdrop_path))

        viewHolder.binding.root.setOnClickListener { onItemclick(mSliderItems[position]) }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        val binding = ItemSliderLayoutBinding.bind(itemView)
    }


}