package com.movieapps.mobile.data.repository

import com.github.ajalt.timberkt.d
import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.coreandroid.functional.Either
import com.movieapps.mobile.coreandroid.network.NetworkChecker
import com.movieapps.mobile.data.datasource.local.NewsLocalDatasource
import com.movieapps.mobile.data.datasource.remote.NewsRemoteDatasource
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.utility.ThreadInfoLogger
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remote: NewsRemoteDatasource,
    private val local: NewsLocalDatasource,
    private val networkChecker: NetworkChecker
) : NewsRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Either<Failure, List<News>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getTopHeadlines(category = category, country = country)

                local.insertNews(response)

                Either.Right(response)
            } else {
                d { "connection : disconnect" }
                // not connected
                val localNews = local.getAllNews()
                d { "get data from local: ${localNews.size}" }
                if (localNews.isEmpty()) {
                    Either.Left(Failure.LocalDataNotFound)
                } else {
                    Either.Right(localNews)
                }
            }
        } catch (ex: IOException) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }
}
