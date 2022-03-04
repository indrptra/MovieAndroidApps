package com.movieapps.mobile.data.datasource.local

import com.github.ajalt.timberkt.d
import com.movieapps.mobile.data.datasource.local.db.AppDatabase
import com.movieapps.mobile.data.datasource.local.entity.toFavoriteEntity
import com.movieapps.mobile.data.datasource.local.entity.toMovieList
import com.movieapps.mobile.domain.entity.PopularMovieList

class MovieLocalDatasourceImpl(private val appDatabase: AppDatabase) : MovieLocalDatasource {

    override suspend fun insertPopularMovie(popularMovie: List<PopularMovieList>) {
    }

    override suspend fun insertFavoriteMovie(popularMovie: PopularMovieList) {
        appDatabase.favoriteDao().insert(popularMovie.toFavoriteEntity())
    }

    override suspend fun getAllFavorite(): List<PopularMovieList> {
        val localMovie = appDatabase.favoriteDao().getAllFavoriteMovie()
        d { "local movie size ${localMovie.size}" }
        val listNews = mutableListOf<PopularMovieList>()
        localMovie.map {
            listNews.add(it.toMovieList())
        }
        return listNews
    }

    override suspend fun getItemFavorite(id: String): List<PopularMovieList> {
        val localMovie = appDatabase.favoriteDao().getItemFavoriteMovie(id = id)
        val listMovie = mutableListOf<PopularMovieList>()
        if (localMovie != null) listMovie.add(localMovie.toMovieList())
        return listMovie
    }

    override suspend fun deleteFavorite(popularMovie: PopularMovieList) {
        val localMovie = appDatabase.favoriteDao().getAllFavoriteMovie()
        localMovie.map {
            if (it?.movieId.equals(popularMovie.id)) {
                appDatabase.favoriteDao().deleteFavoriteMovie(it)
            }
        }
    }
}
