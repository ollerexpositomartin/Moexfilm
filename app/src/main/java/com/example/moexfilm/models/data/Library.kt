package com.example.moexfilm.models.data

data class Library(
    val refreshToken:String,
    val id:String,
    val name:String,
    val content:List<Movie>,
    val type:String,
    val language:String
)
