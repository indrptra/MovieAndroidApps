package com.movieapps.mobile.data.datasource.remote.model

import com.movieapps.mobile.domain.entity.News

data class TopHeadlineResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsResponse>
)

fun TopHeadlineResponse.toNewsList(): List<News> {
    val listNews = mutableListOf<News>()
    articles.map {
        listNews.add(toNews((it)))
    }
    return listNews
}

