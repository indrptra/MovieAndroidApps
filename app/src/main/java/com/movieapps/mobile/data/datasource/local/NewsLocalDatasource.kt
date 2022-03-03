package com.movieapps.mobile.data.datasource.local

import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.PopularMovieList

interface NewsLocalDatasource {
   suspend fun insertNews(news: List<News>)
   suspend fun insertPopularMovie(popularMovie: List<PopularMovieList>)
   suspend fun insertFavoriteMovie(popularMovie: PopularMovieList)
   suspend fun getAllNews(): List<News>
   suspend fun getAllFavorite(): List<PopularMovieList>
   suspend fun getItemFavorite(id: String): List<PopularMovieList>
   suspend fun deleteFavorite(popularMovie: PopularMovieList)
}
