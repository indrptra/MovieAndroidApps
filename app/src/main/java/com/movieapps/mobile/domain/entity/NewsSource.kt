package com.movieapps.mobile.domain.entity

import com.movieapps.mobile.ui.dto.NewsSourceDto

data class NewsSource(
    val id: String,
    val name: String
)

fun NewsSource.toDto(): NewsSourceDto {
    return NewsSourceDto(id, name)
}