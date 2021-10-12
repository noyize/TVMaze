package com.noyize.tvmaze.module.model

data class Show(
    val id : Int,
    val name: String,
    val summary: String,
    val imageUrl: String,
    val language: String,
    val rating: String,
    val status: String
)
