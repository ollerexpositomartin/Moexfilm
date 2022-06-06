package com.example.moexfilm.views.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moexfilm.R
import com.example.moexfilm.application.Application.Access.prefs
import com.example.moexfilm.application.Prefs
import com.example.moexfilm.databinding.FragmentProfileBinding
import com.example.moexfilm.viewModels.ProfileViewModel
import kotlin.system.exitProcess

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObservers()
    }

    private fun setObservers() {
        profileViewModel.librariesScanned.observe(viewLifecycleOwner) {
            binding.tvLibrariesScanned.text = it.toString()
        }
        profileViewModel.moviesScanned.observe(viewLifecycleOwner) {
            binding.tvMoviesScanned.text = it.toString()
        }
        profileViewModel.tvShowsScanned.observe(viewLifecycleOwner) {
            binding.tvTvShowsScanned.text = it.toString()
        }
    }

    private fun setListener() {
        binding.btnSignout.setOnClickListener {
            prefs.closeSession()
            requireActivity().finishAndRemoveTask()
        }
    }
}