package com.movieapps.mobile.data.datasource.remote

import com.movieapps.mobile.domain.entity.PopularMovieList

interface MovieRemoteDatasource {
    suspend fun getPopularMovie(page: Int): List<PopularMovieList>
    suspend fun getTopRatedMovie(page: Int): List<PopularMovieList>
    suspend fun getNowPlayingMovie(page: Int): List<PopularMovieList>
    suspend fun getReviewMovie(page: Int, id: String): List<PopularMovieList>
}