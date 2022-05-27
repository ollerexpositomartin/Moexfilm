package com.example.moexfilm.views.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moexfilm.R
import com.example.moexfilm.application.Prefs
import com.example.moexfilm.databinding.FragmentProfileBinding
import kotlin.system.exitProcess

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.btnSignout.setOnClickListener {
            Prefs.closeSession()
            requireActivity().finishAndRemoveTask()
        }
    }
}