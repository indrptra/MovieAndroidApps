package com.movieapps.mobile.data.datasource.remote

import com.movieapps.mobile.data.datasource.remote.model.toNewsList
import com.movieapps.mobile.data.datasource.remote.service.NewsApiServices
import com.movieapps.mobile.domain.entity.News
import javax.inject.Inject

class NewsRemoteDatasourceImpl @Inject constructor(private val services: NewsApiServices) :
    NewsRemoteDatasource {

    override suspend fun getTopHeadlines(category: String, country: String): List<News> {
        return services.getTopHeadlines(country, category).toNewsList()
    }
}