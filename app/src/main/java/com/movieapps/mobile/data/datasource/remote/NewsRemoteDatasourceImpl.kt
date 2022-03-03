package com.movieapps.mobile.data.datasource.remote

import com.movieapps.mobile.data.datasource.remote.model.toList
import com.movieapps.mobile.data.datasource.remote.model.toNewsList
import com.movieapps.mobile.data.datasource.remote.service.NewsApiServices
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.PopularMovieList
import javax.inject.Inject

class NewsRemoteDatasourceImpl @Inject constructor(private val services: NewsApiServices) :
    NewsRemoteDatasource {

    override suspend fun getTopHeadlines(category: String, country: String): List<News> {
        return services.getTopHeadlines(country, category).toNewsList()
    }

    override suspend fun getPopularMovie(page: Int): List<PopularMovieList> {
        return services.getPopularMovie("8d0353664a00f37531bd43a4c8a9e176", "en-US", page, "US").toList()
    }

    override suspend fun getTopRatedMovie(page: Int): List<PopularMovieList> {
        return services.getTopRatedMovie("8d0353664a00f37531bd43a4c8a9e176", "en-US", page, "US").toList()
    }

    override suspend fun getNowPlayingMovie(page: Int): List<PopularMovieList> {
        return services.getNowPlayingMovie("8d0353664a00f37531bd43a4c8a9e176", "en-US", page, "US").toList()
    }

    override suspend fun getReviewMovie(page: Int, id: String): List<PopularMovieList> {
        return services.getReviewMovie(id, "8d0353664a00f37531bd43a4c8a9e176", "en-US", page).toList()
    }
}
