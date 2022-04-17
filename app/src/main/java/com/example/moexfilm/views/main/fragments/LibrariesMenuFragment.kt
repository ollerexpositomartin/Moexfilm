package com.example.moexfilm.views.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.moexfilm.R
import com.example.moexfilm.databinding.FragmentLibrariesMenuBinding
import com.example.moexfilm.util.Application.Access.clientId
import com.example.moexfilm.viewModels.LibrariesMenuViewModel
import com.example.moexfilm.views.CreateLibraryActivity
import com.example.moexfilm.views.fileExplorer.FileExplorerActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

class LibrariesMenuFragment : Fragment() {
    private lateinit var binding: FragmentLibrariesMenuBinding
    private val librariesMenuViewModel: LibrariesMenuViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLibrariesMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener { startActivity(Intent(requireContext(),CreateLibraryActivity::class.java)) }
    }

}