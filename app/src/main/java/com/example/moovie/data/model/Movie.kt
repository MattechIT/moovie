package com.example.moovie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Domain and Network data model representing a Movie from TMDB.
 */
@Serializable
data class TmdbGenre(
    val id: Int,
    val name: String
)

@Serializable
data class CastMember(
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String? = null,
    val character: String? = null
)

@Serializable
data class CrewMember(
    val id: Int,
    val name: String,
    val job: String,
    val department: String
)

@Serializable
data class Credits(
    val cast: List<CastMember> = emptyList(),
    val crew: List<CrewMember> = emptyList()
)

/**
 * Domain and Network data model representing a Movie from TMDB.
 */
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerialName("release_date") val releaseDate: String = "",
    val runtime: Int? = null,
    val tagline: String? = null,
    val genres: List<TmdbGenre>? = null,
    val credits: Credits? = null
) {
    val combinedGenreIds: List<Int>
        get() = genreIds.ifEmpty { genres?.map { it.id } ?: emptyList() }
}

