package com.movieapps.mobile.data.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.movieapps.mobile.data.datasource.local.db.AppDatabase
import com.movieapps.mobile.data.datasource.local.entity.NewsEntity
import com.movieapps.mobile.data.datasource.local.entity.toNewsEntity
import com.movieapps.mobile.domain.entity.News
import com.movieapps.mobile.domain.entity.NewsSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieLocalDatasourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var appDatabase: AppDatabase

    @MockK
    lateinit var newsDao: NewsDao

    lateinit var movieLocalDatasource: MovieLocalDatasource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieLocalDatasource = MovieLocalDatasourceImpl(appDatabase)
    }

    @Test
    fun `test insertNews`() = runBlockingTest {
        // given
        val news = generateFakeNews()

        coEvery { appDatabase.newsDao().insert(news[0].toNewsEntity()) } returns Unit

        movieLocalDatasource.insertNews(news)

        coVerify { appDatabase.newsDao().insert(news[0].toNewsEntity()) }

    }

    @Test
    fun `test getAllNews`() = runBlockingTest {
        coEvery { appDatabase.newsDao().getAllNews() } returns generateFakeNewsEntity()

        val result = movieLocalDatasource.getAllNews()

        assertEquals(result.size, 1)
    }

    private fun generateFakeNewsEntity(): List<NewsEntity> {
        return listOf(
            NewsEntity(
                title = "title",
                author = "author",
                description = "desc",
                urlToImage = "url",
                url = "url",
                publishedAt = "",
                source = "source"
            )
        )

    }

    private fun generateFakeNews(): List<News> {
        return listOf(
            News(
                source = NewsSource(id = "id", name = ""),
                author = "author",
                title = "title", description = "", url = "url", urlToImage = "", publishedAt = ""
            )
        )
    }
}