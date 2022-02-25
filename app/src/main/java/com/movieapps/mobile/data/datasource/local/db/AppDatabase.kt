package com.movieapps.mobile.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapps.mobile.data.datasource.local.dao.NewsDao
import com.movieapps.mobile.data.datasource.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
