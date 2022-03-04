package com.movieapps.mobile.data.datasource.remote.service

import com.movieapps.mobile.data.datasource.remote.model.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiServices {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovieResponse

    @GET("movie/{id}/reviews")
    suspend fun getReviewMovie(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularMovieResponse
}
