package com.movieapps.mobile.data.datasource.local

import com.github.ajalt.timberkt.d
import com.movieapps.mobile.data.datasource.local.db.AppDatabase
import com.movieapps.mobile.data.datasource.local.entity.toFavoriteEntity
import com.movieapps.mobile.data.datasource.local.entity.toMovieList
import com.movieapps.mobile.data.datasource.local.entity.toNews
import com.movieapps.mobile.data.datasource.local.entity.toNewsEntity
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.PopularMovieList

class NewsLocalDatasourceImpl(private val appDatabase: AppDatabase) : NewsLocalDatasource {

    override suspend fun insertNews(news: List<News>) {
        news.map {
            d { "insert to local data ${it.title}" }
            appDatabase.newsDao().insert(it.toNewsEntity())
        }
    }

    override suspend fun insertPopularMovie(popularMovie: List<PopularMovieList>) {
    }

    override suspend fun insertFavoriteMovie(popularMovie: PopularMovieList) {
        appDatabase.favoriteDao().insert(popularMovie.toFavoriteEntity())
    }

    override suspend fun getAllNews(): List<News> {
        val localNews = appDatabase.newsDao().getAllNews()
        d { "local news size ${localNews.size}" }
        val listNews = mutableListOf<News>()
        localNews.map {
            listNews.add(it.toNews())
        }
        return listNews
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
