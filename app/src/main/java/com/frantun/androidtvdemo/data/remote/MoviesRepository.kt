package com.frantun.androidtvdemo.data.remote

import com.frantun.androidtvdemo.domain.Category
import com.frantun.androidtvdemo.domain.Movie

class MoviesRepository(private val apiKey: String) {

    suspend fun getCategories(): Map<Category, List<Movie>> {
        return Category.entries.associateWith { category ->
            RemoteConnection
                .service
                .getPopularMovies(apiKey, category.id)
                .results.map { it.toDomainMovie() }
        }
    }
}
