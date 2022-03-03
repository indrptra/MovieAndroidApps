package com.movieapps.mobile.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieapps.mobile.domain.entity.PopularMovieList

@Entity(tableName = "Favorite")
data class FavoriteMovieEntity(
    val movieId: String,
    val poster_path: String,
    val overview: String,
    val content: String,
    val author: String,
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun PopularMovieList.toFavoriteEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        title = this.title,
        author = this.author,
        movieId = this.id,
        poster_path = this.poster_path,
        overview = this.overview,
        content = this.content
    )
}

fun FavoriteMovieEntity.toMovieList(): PopularMovieList {
    return PopularMovieList(
        title = this.title,
        author = this.author,
        id = this.movieId,
        poster_path = this.poster_path,
        overview = this.overview,
        content = this.content
    )
}
