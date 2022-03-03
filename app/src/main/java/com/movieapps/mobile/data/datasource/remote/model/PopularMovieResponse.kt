package com.movieapps.mobile.data.datasource.remote.model

import com.movieapps.mobile.domain.entity.PopularMovieList

data class PopularMovieResponse(
    val page: Int,
    val results: List<PopularMovieResultResponse>
)

data class PopularMovieResultResponse(
    val id: String,
    val poster_path: String,
    val overview: String,
    val content: String,
    val author: String,
    val title: String
)

fun PopularMovieResponse.toList(): List<PopularMovieList> {
    val listNews = mutableListOf<PopularMovieList>()
    results.map {
        listNews.add(toMovie((it)))
    }
    return listNews
}

fun toMovie(data: PopularMovieResultResponse): PopularMovieList {
    return PopularMovieList(
        id = data.id ?: "",
        title = data.title ?: "",
        overview = data.overview ?: "",
        content = data.content ?: "",
        author = data.author ?: "",
        poster_path = data.poster_path ?: ""
    )
}