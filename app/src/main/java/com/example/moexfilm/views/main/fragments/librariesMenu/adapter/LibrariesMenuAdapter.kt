package com.example.moexfilm.views.main.fragments.librariesMenu.adapter

import android.view.View
import com.example.moexfilm.models.data.Library
import com.example.moexfilm.models.data.Movie
import com.example.moexfilm.models.repository.FirebaseDBRepository
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.firebase.ui.database.FirebaseListOptions.Builder
import com.google.firebase.ktx.Firebase

class LibrariesMenuAdapter(options: FirebaseListOptions<Library>) :FirebaseListAdapter<Library>(
    Builder<Library>().apply {

        .setQuery(FirebaseDBRepository.getLibraries(),Library::class.java)
    }.build()){
    override fun populateView(v: View, model: Library, position: Int) {
        TODO("Not yet implemented")
    }
}