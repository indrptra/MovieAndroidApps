package com.movieapps.mobile.ui.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularMovieListDTO(
    val id: String,
    val poster_path: String,
    val overview: String,
    val author: String,
    val title: String
) : Parcelable
