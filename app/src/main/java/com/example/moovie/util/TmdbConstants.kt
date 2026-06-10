package com.example.moovie.util

/**
 * Constants and utility helpers for TMDB API and image paths.
 */
object TmdbConstants {
    const val BASE_URL = "https://api.themoviedb.org/3"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"

    /**
     * Resolves the full URL for a movie poster.
     */
    fun getPosterUrl(path: String, size: String = "w500"): String = "$IMAGE_BASE_URL/$size$path"

    /**
     * Resolves the full URL for a movie backdrop image.
     */
    fun getBackdropUrl(path: String, size: String = "w780"): String = "$IMAGE_BASE_URL/$size$path"

    /**
     * Resolves the full URL for a cast/crew member profile picture.
     */
    fun getProfileUrl(path: String, size: String = "w185"): String = "$IMAGE_BASE_URL/$size$path"
}
