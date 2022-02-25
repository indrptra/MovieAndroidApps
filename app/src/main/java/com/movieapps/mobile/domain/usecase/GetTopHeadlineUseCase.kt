package com.movieapps.mobile.domain.usecase

import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.coreandroid.usecase.UseCase
import com.movieapps.mobile.data.repository.NewsRepository
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.utility.ThreadInfoLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopHeadlineUseCase @Inject constructor(private val repository: NewsRepository) :
    UseCase<List<News>, GetTopHeadlineUseCase.TopHeadlineParam>() {

    override suspend fun run(params: TopHeadlineParam): Either<Failure, List<News>> =
        withContext(Dispatchers.IO) {
            ThreadInfoLogger.logThreadInfo("get top headline usecase")
            repository.getTopHeadlines(params.country, params.category)
        }

    data class TopHeadlineParam(
        val country: String,
        val category: String
    )
}