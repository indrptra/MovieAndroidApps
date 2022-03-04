package com.movieapps.mobile.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movieapps.mobile.coreandroid.network.NetworkChecker
import com.movieapps.mobile.data.datasource.local.MovieLocalDatasource
import com.movieapps.mobile.data.datasource.remote.MovieRemoteDatasource
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.NewsSource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
class MovieRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var movieRepository: MovieRepository

    @MockK
    lateinit var movieRemoteDatasource: MovieRemoteDatasource

    @MockK
    lateinit var movieLocalDatasource: MovieLocalDatasource

    @MockK
    lateinit var networkChecker: NetworkChecker

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepository =
            MovieRepositoryImpl(movieRemoteDatasource, movieLocalDatasource, networkChecker)
    }

    @Test
    fun `test getTopHeadlines throw exceptions`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } throws IOException()

        val result = movieRepository.getTopHeadlines(country = "us", category = "technology")

        assertTrue(result.isLeft)
    }

    @Test
    fun `test getTopHeadlines network is connected should return data`() = runBlockingTest {
        // given
        val country = "us"
        val category = "technology"

        // network down
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { movieLocalDatasource.insertNews(generateFakeData()) } returns Unit

        coEvery {
            movieRemoteDatasource.getTopHeadlines(
                category,
                country
            )
        } returns generateFakeData()

        val result = movieRepository.getTopHeadlines(country, category)

        coVerify { movieLocalDatasource.insertNews(generateFakeData()) }

        assertTrue(result.isRight)
    }

    @Test
    fun `test getTopHeadlines network is down should return local data`() = runBlockingTest {
        // given
        val country = "us"
        val category = "technology"

        // network down
        every { networkChecker.isNetworkConnected() } returns false

        // local return data
        coEvery { movieLocalDatasource.getAllNews() } returns generateFakeData()

        // when
        val result = movieRepository.getTopHeadlines(country, category)
        // then
        assertTrue(result.isRight)
    }

    private fun generateFakeData(): List<News> {
        return listOf(
            News(
                source = NewsSource(id = "anu", name = "title"),
                author = "",
                title = "title",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = ""
            )
        )
    }

    @Test
    fun `test getTopHeadlines network is down and local data null should return failure`() =
        runBlockingTest {
            // given
            val country = "us"
            val category = "technology"

            // network down
            every { networkChecker.isNetworkConnected() } returns false
            coEvery { movieLocalDatasource.getAllNews() } returns emptyList()

            // when
            val result = movieRepository.getTopHeadlines(country, category)
            // then
            assertTrue(result.isLeft)
        }
}