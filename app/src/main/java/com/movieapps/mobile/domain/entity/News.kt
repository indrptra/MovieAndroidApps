package com.movieapps.mobile.domain.entity

import com.movieapps.mobile.ui.dto.NewsDto

data class News(
    val source: NewsSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)


fun News.toDto(): NewsDto {
    return with(this) {
        NewsDto(source.toDto(), author, title, description, url, urlToImage, publishedAt)
    }
}