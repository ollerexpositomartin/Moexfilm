package com.example.moexfilm.views.main.fragments.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moexfilm.R
import com.example.moexfilm.databinding.FragmentHomeBinding
import com.example.moexfilm.databinding.ItemListFragmentHomeLayoutBinding
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.viewModels.HomeFragmentViewModel
import com.example.moexfilm.views.main.fragments.homeFragment.adapters.PopularAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations


class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    lateinit var sliderAdapter:SliderAdapter
    val homeFragmentViewModel:HomeFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sliderAdapter = SliderAdapter()
        setSlider()
        initObserverRandom()
        initObserverPopular()
    }

    private fun initObserverRandom() {
        homeFragmentViewModel.mutableListRandomItemsMutableLiveData.observe(viewLifecycleOwner){ randomItems ->
            sliderAdapter.renewItems(randomItems)
        }
    }

    private fun onPopularItemClick(movie: Movie){

    }

    private fun initObserverPopular(){
        homeFragmentViewModel.mutableListPopularMoviesMutableLiveData.observe(viewLifecycleOwner){ popularItems ->
           if(popularItems.isNotEmpty()){
               val adapter = PopularAdapter { onPopularItemClick(it) }
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