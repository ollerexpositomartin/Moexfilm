package com.example.moexfilm.models.interfaces.listeners

import com.example.moexfilm.models.data.Library

interface FireBaseAdapterListener {
    fun sizeListener(size:Int)
    fun onClickItemListener(library: Library)
}