package com.movieapps.mobile.data.datasource.remote

import com.movieapps.mobile.domain.entity.News

interface NewsRemoteDatasource {
    suspend fun getTopHeadlines(category: String, country: String): List<News>
}