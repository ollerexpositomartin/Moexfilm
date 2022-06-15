package com.example.moexfilm.views.main.fragments.homeFragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.FragmentHomeBinding
import com.example.moexfilm.databinding.ItemListFragmentHomeLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.TMDBItem
import com.example.moexfilm.models.interfaces.Playable
import com.example.moexfilm.viewModels.HomeViewModel
import com.example.moexfilm.views.VideoPlayerActivity
import com.example.moexfilm.views.library.adapters.LibraryItemsAdapter
import com.example.moexfilm.views.main.fragments.homeFragment.adapters.MediaInProgressAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    private lateinit var sliderAdapter:SliderAdapter

    private val homeFragmentViewModel:HomeViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            sliderAdapter = SliderAdapter(){onMediaInProgressClick(it)}
            setSlider()
            initObserverRandom()
            initObserverMediaInProgress()
            initObserverPopular()
    }

    private fun initObserverRandom() {
        homeFragmentViewModel.mutableListRandomItemsMutableLiveData.observe(viewLifecycleOwner) { randomItems ->
            if(randomItems.isNotEmpty()) {
                sliderAdapter.renewItems(randomItems)
                binding.infoLayout.visibility = View.INVISIBLE
            }
            if(randomItems.isEmpty())
                binding.infoLayout.visibility = View.VISIBLE

        }
    }

    private fun initObserverMediaInProgress(){
        homeFragmentViewModel.mutableListMediaInProgressMutableLiveData.observe(viewLifecycleOwner) { mediaInProgress ->
            if(mediaInProgress.isNotEmpty()){
                val adapter = MediaInProgressAdapter { onMediaInProgressClick(it)}
                adapter.submitList(mediaInProgress)
                addContentToLinearLayout(onInflateItemView("En progreso",adapter))
                if(mediaInProgress.isNotEmpty())
                    binding.infoLayout.visibility = View.INVISIBLE
            }
        }
    }

    private fun onMediaInProgressClick(media:TMDBItem) {
        val mediaInfo:Playable = media as Playable

        startActivity(Intent(requireContext(),VideoPlayerActivity::class.java).apply {
            putExtra("CONTENT",media)
            putExtra("PROGRESS",mediaInfo.playedTime())
        })
    }

    private fun onPopularItemClick(media: TMDBItem){
        startActivity(Intent(requireContext(),VideoPlayerActivity::class.java).apply { putExtra("CONTENT",media) })
    }

    private fun initObserverPopular(){
        homeFragmentViewModel.mutableListPopularMoviesMutableLiveData.observe(viewLifecycleOwner){ popularItems ->
           if(popularItems.isNotEmpty()){
               val adapter = LibraryItemsAdapter { onPopularItemClick(it) }
               adapter.submitList(popularItems)
               addContentToLinearLayout(onInflateItemView("Popular",adapter))
           }
        }
    }

    private fun onInflateItemView(title:String,adapter:RecyclerView.Adapter<*>):View{
        val bindingItemLinearLayout  =  ItemListFragmentHomeLayoutBinding.inflate(layoutInflater)
        bindingItemLinearLayout.headTitle.text = title
        bindingItemLinearLayout.recyclerView.adapter = adapter
        bindingItemLinearLayout.recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        bindingItemLinearLayout.recyclerView.setHasFixedSize(true)

        return bindingItemLinearLayout.root
    }

    private fun addContentToLinearLayout(onInflateItemView: View) {
        binding.LinearItems.addView(onInflateItemView)
    }

    private fun checkLayoutContainView(onInflateItemView: View, layout: LinearLayout):Boolean {
        val view  = ItemListFragmentHomeLayoutBinding.bind(onInflateItemView)

        for(views in layout.iterator()){
            val tempView = ItemListFragmentHomeLayoutBinding.bind(views)
            if(tempView.headTitle == view.headTitle)
                return true
        }

        return false
    }

    private fun setSlider() {
        binding.imageSlider.setSliderAdapter(sliderAdapter)
        binding.imageSlider.apply {
            setIndicatorEnabled(true)
            indicatorSelectedColor = resources.getColor(R.color.accent_moexfilm)
            indicatorUnselectedColor = resources.getColor(R.color.white)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)

            startAutoCycle()
        }
    }
}