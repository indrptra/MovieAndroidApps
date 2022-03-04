package com.movieapps.mobile.data.repository

import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.PopularMovieList

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, category: String): Either<Failure, List<News>>
    suspend fun getPopularMovie(page: Int): Either<Failure, List<PopularMovieList>>
    suspend fun getTopRatedMovie(page: Int): Either<Failure, List<PopularMovieList>>
    suspend fun getNowPlayingMovie(page: Int): Either<Failure, List<PopularMovieList>>
    suspend fun getReviewMovie(page: Int, id: String): Either<Failure, List<PopularMovieList>>
    suspend fun getFavoriteMovie(): Either<Failure, List<PopularMovieList>>
    suspend fun getItemFavoriteMovie(id: String): Either<Failure, List<PopularMovieList>>
    suspend fun setItemFavoriteMovie(data: PopularMovieList): Either<Failure, List<PopularMovieList>>
    suspend fun deleteItemFavoriteMovie(data: PopularMovieList): Either<Failure, List<PopularMovieList>>
}