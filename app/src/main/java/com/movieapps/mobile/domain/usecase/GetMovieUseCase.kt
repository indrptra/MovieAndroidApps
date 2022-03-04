package com.movieapps.mobile.domain.usecase

import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.coreandroid.usecase.UseCase
import com.movieapps.mobile.data.repository.MovieRepository
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.utility.ThreadInfoLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) :
    UseCase<List<PopularMovieList>, GetMovieUseCase.MovieParam>() {

    override suspend fun run(params: MovieParam): Either<Failure, List<PopularMovieList>> =
        withContext(Dispatchers.IO) {
            ThreadInfoLogger.logThreadInfo("get popular movie usecase")
            when (params.type) {
                0 -> repository.getPopularMovie(params.page)
                1 -> repository.getTopRatedMovie(params.page)
                2 -> repository.getNowPlayingMovie(params.page)
                3 -> repository.getReviewMovie(params.page, params.id!!)
                4 -> repository.getFavoriteMovie()
                5 -> repository.getItemFavoriteMovie(id = params.id!!)
                6 -> repository.setItemFavoriteMovie(data = params.data!!)
                else -> repository.deleteItemFavoriteMovie(data = params.data!!)
            }
        }

    data class MovieParam(
        val page: Int,
        val type: Int,
        val id: String? = "",
        val data: PopularMovieList? = null
    )
}