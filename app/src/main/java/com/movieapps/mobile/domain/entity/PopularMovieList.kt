package com.movieapps.mobile.domain.entity

import com.movieapps.mobile.ui.dto.PopularMovieListDTO

data class PopularMovieList(
    val id: String,
    val poster_path: String,
    val overview: String,
    val content: String,
    val author: String,
    val title: String
)

fun PopularMovieList.toDto(): PopularMovieListDTO {
    return with(this) {
        PopularMovieListDTO(id, poster_path, overview, author, title)
    }
}