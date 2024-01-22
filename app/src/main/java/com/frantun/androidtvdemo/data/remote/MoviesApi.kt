package com.frantun.androidtvdemo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int = 100,
    ): MoviesDto
}
