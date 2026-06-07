package com.example.moovie.data.repository

import com.example.moovie.data.model.Cinema

/**
 * Static catalog containing mockup local cinemas data mapped with dynamic movie IDs.
 */
object MockCinemaCatalog {

    /**
     * Generate list of mock cinemas using active movie IDs representing the active catalog.
     */
    fun getMockCinemas(happyMovieIds: List<Int>, energeticMovieIds: List<Int>): List<Cinema> {
        val allMovieIds = happyMovieIds + energeticMovieIds
        return listOf(
            Cinema(
                id = 1,
                name = "CinemaCity Ravenna",
                latitude = 44.4180,
                longitude = 12.1713,
                address = "Via Secondo Bini, 7, Ravenna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds
            ),
            Cinema(
                id = 2,
                name = "UCI Cinemas Romagna",
                latitude = 44.1664,
                longitude = 12.4281,
                address = "Piazza Fratelli Lumière, 22, Savignano sul Rubicone",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds
            ),
            Cinema(
                id = 3,
                name = "Cinema Eliseo Cesena",
                latitude = 44.1395,
                longitude = 12.2468,
                address = "Viale Giosuè Carducci, 7, Cesena",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2)
            ),
            Cinema(
                id = 4,
                name = "Multisala Astoria Forlì",
                latitude = 44.2057,
                longitude = 12.0461,
                address = "Viale dell'Appennino, 313, Forlì",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3)
            )
        )
    }
}
