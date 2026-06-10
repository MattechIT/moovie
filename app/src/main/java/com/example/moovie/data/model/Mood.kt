package com.example.moovie.data.model

import androidx.compose.ui.graphics.Color
import com.example.moovie.R

/**
 * Represents a Mood that filters TMDB movies by associated Genre enums.
 */
enum class Mood(
    val titleRes: Int,
    val emoji: String,
    val genres: List<Genre>,
    val colorAccent: Color
) {
    HAPPY(
        titleRes = R.string.mood_happy_title,
        emoji = "😊",
        genres = listOf(Genre.COMEDY, Genre.ROMANCE),
        colorAccent = Color(0xFFFFD700)
    ),
    SCARED(
        titleRes = R.string.mood_scared_title,
        emoji = "😱",
        genres = listOf(Genre.HORROR, Genre.THRILLER),
        colorAccent = Color(0xFFE50914)
    ),
    THOUGHTFUL(
        titleRes = R.string.mood_thoughtful_title,
        emoji = "🤔",
        genres = listOf(Genre.DRAMA, Genre.MYSTERY),
        colorAccent = Color(0xFF9C27B0)
    ),
    ENERGETIC(
        titleRes = R.string.mood_energetic_title,
        emoji = "⚡",
        genres = listOf(Genre.ACTION, Genre.ADVENTURE),
        colorAccent = Color(0xFFFF5722)
    ),
    DREAMY(
        titleRes = R.string.mood_dreamy_title,
        emoji = "🌌",
        genres = listOf(Genre.FANTASY, Genre.SCIENCE_FICTION),
        colorAccent = Color(0xFF00BCD4)
    ),
    RELAXED(
        titleRes = R.string.mood_relaxed_title,
        emoji = "🍿",
        genres = listOf(Genre.ANIMATION, Genre.FAMILY),
        colorAccent = Color(0xFF4CAF50)
    );

    companion object {
        /**
         * Parsing fallback if needed.
         */
        fun fromString(name: String?): Mood {
            return try {
                name?.let { valueOf(it.uppercase()) } ?: HAPPY
            } catch (_: Exception) {
                HAPPY
            }
        }
    }
}
