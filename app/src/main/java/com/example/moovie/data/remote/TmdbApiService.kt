package com.example.moovie.data.remote

import com.example.moovie.data.model.Movie

/**
 * Interface defining raw remote data operations for the TMDB API.
 * Keeps network calls separated from database caching and mock fallback logic.
 */
interface TmdbApiService {
    
    /**
     * Checks if a valid TMDB API key is configured.
     */
    fun isApiKeyConfigured(): Boolean

    /**
     * Fetches movies matching the specified genre parameters.
     */
    suspend fun getMoviesByGenres(genresParam: String): List<Movie>

    /**
     * Fetches details for a single movie by its unique ID.
     */
    suspend fun getMovieById(movieId: Int): Movie

    /**
     * Searches for movies containing the specified text query.
     */
    suspend fun searchMovies(query: String): List<Movie>
}
