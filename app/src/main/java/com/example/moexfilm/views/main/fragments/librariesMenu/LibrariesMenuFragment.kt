package com.example.moexfilm.views.main.fragments.librariesMenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.moexfilm.databinding.FragmentLibrariesMenuBinding
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.interfaces.listeners.FireBaseAdapterListener
import com.example.moexfilm.views.CreateLibraryActivity
import com.example.moexfilm.views.main.fragments.librariesMenu.adapter.LibrariesMenuAdapter

class LibrariesMenuFragment : Fragment(),FireBaseAdapterListener {
    private lateinit var binding: FragmentLibrariesMenuBinding
    private lateinit var adapter:LibrariesMenuAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLibrariesMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener { startActivity(Intent(requireContext(),CreateLibraryActivity::class.java)) }
        adapter = LibrariesMenuAdapter(this)
        setRecycler()
    }

    private fun setRecycler() {
        adapter.startListening()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun sizeListener(size: Int) {
        if(size>0){binding.noLibrariesIndicator.visibility = View.INVISIBLE; return}
        binding.noLibrariesIndicator.visibility = View.VISIBLE
    }

    override fun onClickItemListener(library: Library) {

    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }
}