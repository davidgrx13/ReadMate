package com.example.readmatetasks.data.model

import androidx.annotation.DrawableRes

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val totalPages: Int,
    @DrawableRes val coverImage: Int,
    @DrawableRes val authorImage: Int,
    val synopsis: String,
    val about: String
)