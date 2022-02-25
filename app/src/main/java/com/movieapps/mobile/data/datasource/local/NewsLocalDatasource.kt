package com.movieapps.mobile.data.datasource.local

import com.movieapps.mobile.domain.entity.News

interface NewsLocalDatasource {
   suspend fun insertNews(news: List<News>)
   suspend fun getAllNews(): List<News>
}
