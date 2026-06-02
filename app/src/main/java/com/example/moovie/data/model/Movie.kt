package com.example.moovie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Domain and Network data model representing a Movie from TMDB.
 */
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("release_date") val releaseDate: String = ""
)
