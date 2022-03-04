package com.movieapps.mobile.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieapps.mobile.data.datasource.local.dao.FavoriteDao
import com.movieapps.mobile.data.datasource.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
