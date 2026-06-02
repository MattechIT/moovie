package com.example.moovie.data.model

import com.example.moovie.R

/**
 * Enum representing official TMDB movie genres, mapped to localized string resources.
 */
enum class Genre(val id: Int, val titleRes: Int) {
    ACTION(28, R.string.genre_action),
    ADVENTURE(12, R.string.genre_adventure),
    ANIMATION(16, R.string.genre_animation),
    COMEDY(35, R.string.genre_comedy),
    CRIME(80, R.string.genre_crime),
    DOCUMENTARY(99, R.string.genre_documentary),
    DRAMA(18, R.string.genre_drama),
    FAMILY(10751, R.string.genre_family),
    FANTASY(14, R.string.genre_fantasy),
    HISTORY(36, R.string.genre_history),
    HORROR(27, R.string.genre_horror),
    MUSIC(10402, R.string.genre_music),
    MYSTERY(9648, R.string.genre_mystery),
    ROMANCE(10749, R.string.genre_romance),
    SCIENCE_FICTION(878, R.string.genre_sci_fi),
    TV_MOVIE(10770, R.string.genre_tv_movie),
    THRILLER(53, R.string.genre_thriller),
    WAR(10752, R.string.genre_war),
    WESTERN(37, R.string.genre_western);

    companion object {
        /**
         * Safely resolve a Genre enum by its numeric ID.
         */
        fun fromId(id: Int): Genre? {
            return entries.find { it.id == id }
        }
    }
}
