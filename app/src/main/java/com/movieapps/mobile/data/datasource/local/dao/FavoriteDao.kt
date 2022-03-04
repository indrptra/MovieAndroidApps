package com.movieapps.mobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.movieapps.mobile.data.datasource.local.entity.FavoriteMovieEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    suspend fun getAllFavoriteMovie(): List<FavoriteMovieEntity>

    @Query("SELECT * FROM Favorite WHERE movieId = :id")
    suspend fun getItemFavoriteMovie(id: String): FavoriteMovieEntity

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)

    @Insert
    suspend fun insert(movie: FavoriteMovieEntity)
}