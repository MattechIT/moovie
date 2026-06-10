package com.example.moovie.data.repository

import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining movie data operations.
 */
interface MovieRepository {
    /**
     * Retrieve movies filtered by user state of mind (Mood).
     */
    suspend fun getMoviesByMood(mood: Mood, page: Int = 1): Result<List<Movie>>

    /**
     * Retrieve a single movie's details by its unique ID.
     */
    suspend fun getMovieById(movieId: Int): Result<Movie>

    /**
     * Observe the list of favorite movies.
     */
    fun getFavoriteMovies(): Flow<List<Movie>>

    /**
     * Observe the list of watchlist movies.
     */
    fun getWatchlistMovies(): Flow<List<Movie>>

    /**
     * Observe whether a specific movie is marked as a favorite.
     */
    fun isFavorite(movieId: Int): Flow<Boolean>

    /**
     * Observe whether a specific movie is in the watchlist.
     */
    fun isWatchlisted(movieId: Int): Flow<Boolean>

    /**
     * Toggle favorite status of a movie locally.
     */
    suspend fun toggleFavorite(movie: Movie)

    /**
     * Toggle watchlist status of a movie locally.
     */
    suspend fun toggleWatchlist(movie: Movie)

    /**
     * Search movies by query string from remote API or local mock catalog.
     */
    suspend fun searchMovies(query: String): Result<List<Movie>>

    /**
     * Refresh all locally saved movies (favorites/watchlist) from the remote API
     * using the current language settings.
     */
    suspend fun updateSavedMoviesLanguage()

    /**
     * Retrieve movies starring a specific actor.
     */
    suspend fun getMoviesByActor(actorId: Int, page: Int = 1): Result<List<Movie>>
}

