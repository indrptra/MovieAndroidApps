package com.movieapps.mobile.data.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movieapps.mobile.data.datasource.remote.model.NewsResponse
import com.movieapps.mobile.data.datasource.remote.model.SourceResponse
import com.movieapps.mobile.data.datasource.remote.model.TopHeadlineResponse
import com.movieapps.mobile.data.datasource.remote.service.MovieApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteDatasourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var newsRemoteDatasourceImpl: MovieRemoteDatasourceImpl

    @MockK
    lateinit var service: MovieApiServices

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        newsRemoteDatasourceImpl = MovieRemoteDatasourceImpl(service)
    }

    @Test
    fun `test getTopHeadlines should return list news`() = runBlockingTest {
        // given
        val category = "technology"
        val country = "us"

        coEvery { service.getTopHeadlines(country, category) } returns generateFakeNews()

        // when
        val result = newsRemoteDatasourceImpl.getTopHeadlines(category, country)

        coVerify { service.getTopHeadlines(category = category, country = country) }

        assertEquals(1, result.size)
    }

    private fun generateFakeNews(): TopHeadlineResponse {
        return TopHeadlineResponse(
            status = "success",
            totalResults = 1,
            articles = listOf(
                NewsResponse(
                    source = SourceResponse(id = "1", name = "ok")
                )
            )
        )
    }
}