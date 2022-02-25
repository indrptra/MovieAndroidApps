package com.movieapps.mobile.data.repository

import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.domain.entity.News

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, category: String): Either<Failure, List<News>>
}