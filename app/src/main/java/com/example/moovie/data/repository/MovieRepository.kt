package com.example.moovie.data.repository

import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie

/**
 * Interface defining movie data operations.
 */
interface MovieRepository {
    /**
     * Retrieve movies filtered by user state of mind (Mood).
     */
    suspend fun getMoviesByMood(mood: Mood): Result<List<Movie>>

    /**
     * Retrieve a single movie's details by its unique ID.
     */
    suspend fun getMovieById(movieId: Int): Result<Movie>
}
