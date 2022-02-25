package com.movieapps.mobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.movieapps.mobile.data.datasource.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    suspend fun getAllNews(): List<NewsEntity>

    @Insert
    suspend fun insert(news: NewsEntity)
}
