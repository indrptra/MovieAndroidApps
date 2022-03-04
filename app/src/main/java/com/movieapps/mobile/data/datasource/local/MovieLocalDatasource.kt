package com.movieapps.mobile.data.datasource.local

import com.movieapps.mobile.domain.entity.PopularMovieList

interface MovieLocalDatasource {
    suspend fun insertPopularMovie(popularMovie: List<PopularMovieList>)
    suspend fun insertFavoriteMovie(popularMovie: PopularMovieList)
    suspend fun getAllFavorite(): List<PopularMovieList>
    suspend fun getItemFavorite(id: String): List<PopularMovieList>
    suspend fun deleteFavorite(popularMovie: PopularMovieList)
}
