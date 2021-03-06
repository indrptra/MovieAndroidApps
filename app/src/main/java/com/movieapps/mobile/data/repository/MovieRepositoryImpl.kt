package com.movieapps.mobile.data.repository

import com.github.ajalt.timberkt.d
import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.coreandroid.network.NetworkChecker
import com.movieapps.mobile.data.datasource.local.MovieLocalDatasource
import com.movieapps.mobile.data.datasource.remote.MovieRemoteDatasource
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.utility.ThreadInfoLogger
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieRemoteDatasource,
    private val local: MovieLocalDatasource,
    private val networkChecker: NetworkChecker
) : MovieRepository {

    override suspend fun getPopularMovie(page: Int): Either<Failure, List<PopularMovieList>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getPopularMovie(page = 1)

                local.insertPopularMovie(response)

                Either.Right(response)
            } else {
                Either.Left(Failure.LocalDataNotFound)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun getTopRatedMovie(page: Int): Either<Failure, List<PopularMovieList>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getTopRatedMovie(page = 1)

                local.insertPopularMovie(response)

                Either.Right(response)
            } else {
                Either.Left(Failure.LocalDataNotFound)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun getNowPlayingMovie(page: Int): Either<Failure, List<PopularMovieList>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getNowPlayingMovie(page = 1)

                local.insertPopularMovie(response)

                Either.Right(response)
            } else {
                Either.Left(Failure.LocalDataNotFound)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun getReviewMovie(page: Int, id: String): Either<Failure, List<PopularMovieList>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getReviewMovie(page = 1, id = id)

                local.insertPopularMovie(response)

                Either.Right(response)
            } else {
                Either.Left(Failure.LocalDataNotFound)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun getFavoriteMovie(): Either<Failure, List<PopularMovieList>> {
        return try {
            val localFavorite = local.getAllFavorite()
            if (localFavorite.isEmpty()) {
                Either.Left(Failure.LocalDataNotFound)
            } else {
                Either.Right(localFavorite)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun getItemFavoriteMovie(id: String): Either<Failure, List<PopularMovieList>> {
        return try {
            val localFavorite = local.getItemFavorite(id)
            if (localFavorite == null) {
                Either.Left(Failure.LocalDataNotFound)
            } else {
                Either.Right(localFavorite)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun setItemFavoriteMovie(data: PopularMovieList): Either<Failure, List<PopularMovieList>> {
        return try {
            val localFavorite = local.insertFavoriteMovie(data)
            val listMovie = mutableListOf<PopularMovieList>()
            listMovie.add(data)
            if (localFavorite == null) {
                Either.Left(Failure.LocalDataNotFound)
            } else {
                Either.Right(listMovie)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun deleteItemFavoriteMovie(data: PopularMovieList): Either<Failure, List<PopularMovieList>> {
        return try {
            val localFavorite = local.deleteFavorite(data)
            val listMovie = mutableListOf<PopularMovieList>()
            listMovie.add(data)
            if (localFavorite == null) {
                Either.Left(Failure.LocalDataNotFound)
            } else {
                Either.Right(listMovie)
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }
}
