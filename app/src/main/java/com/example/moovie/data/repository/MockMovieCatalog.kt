package com.example.moovie.data.repository

import com.example.moovie.data.model.Genre
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie

/**
 * Static catalog containing high-fidelity offline mock movies for testing and fallback mode.
 * Kept separated from repository logic to ensure clean architecture and modularity.
 */
object MockMovieCatalog {

    /**
     * Retrieve predefined mock movies tailored for the specified mood.
     */
    fun getMockMoviesForMood(mood: Mood): List<Movie> {
        return when (mood) {
            Mood.HAPPY -> listOf(
                Movie(
                    id = 1022789,
                    title = "Inside Out 2",
                    overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust aren't sure how to feel when Anxiety shows up.",
                    posterPath = "/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
                    voteAverage = 7.6,
                    genreIds = listOf(Genre.ANIMATION.id, Genre.COMEDY.id, Genre.FAMILY.id),
                    releaseDate = "2024-06-12"
                ),
                Movie(
                    id = 77338,
                    title = "The Intouchables",
                    overview = "An unusual friendship develops between a wealthy aristocrat, paralyzed from a paragliding accident, and a young man from the projects who is hired as his caregiver.",
                    posterPath = "/o5bCg0qLd61B4A7nJ26bHPhQWst.jpg",
                    voteAverage = 8.3,
                    genreIds = listOf(Genre.DRAMA.id, Genre.COMEDY.id),
                    releaseDate = "2011-11-02"
                ),
                Movie(
                    id = 313369,
                    title = "La La Land",
                    overview = "Mia, an aspiring actress, and Sebastian, a dedicated jazz musician, struggle to make ends meet in a city known for crushing hopes and breaking hearts.",
                    posterPath = "/uC6TTUhXIjmgC21Z38Vv3n6V2wA.jpg",
                    voteAverage = 7.9,
                    genreIds = listOf(Genre.COMEDY.id, Genre.DRAMA.id, Genre.ROMANCE.id),
                    releaseDate = "2016-11-29"
                )
            )
            Mood.SCARED -> listOf(
                Movie(
                    id = 138843,
                    title = "The Conjuring",
                    overview = "Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.",
                    posterPath = "/w9g3i6q11n7ilQ7Ue0a6d5m6G.jpg",
                    voteAverage = 7.5,
                    genreIds = listOf(Genre.HORROR.id, Genre.THRILLER.id),
                    releaseDate = "2013-07-18"
                ),
                Movie(
                    id = 447332,
                    title = "A Quiet Place",
                    overview = "A family is forced to live in silence while hiding from monsters with ultra-sensitive hearing.",
                    posterPath = "/n31v6A5FWCIqSTg15uH25JXYaju.jpg",
                    voteAverage = 7.4,
                    genreIds = listOf(Genre.HORROR.id, Genre.THRILLER.id, Genre.SCIENCE_FICTION.id),
                    releaseDate = "2018-04-03"
                ),
                Movie(
                    id = 807,
                    title = "Se7en",
                    overview = "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.",
                    posterPath = "/69EFgPfmgwH27W8o95456Elbq7a.jpg",
                    voteAverage = 8.4,
                    genreIds = listOf(Genre.CRIME.id, Genre.MYSTERY.id, Genre.THRILLER.id),
                    releaseDate = "1995-09-22"
                )
            )
            Mood.THOUGHTFUL -> listOf(
                Movie(
                    id = 157336,
                    title = "Interstellar",
                    overview = "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel.",
                    posterPath = "/gEU2QniE6E7vKIvSI67i23pUjJp.jpg",
                    voteAverage = 8.4,
                    genreIds = listOf(Genre.ADVENTURE.id, Genre.DRAMA.id, Genre.SCIENCE_FICTION.id),
                    releaseDate = "2014-11-05"
                ),
                Movie(
                    id = 278,
                    title = "The Shawshank Redemption",
                    overview = "Framed in the 1940s for the double murder of his wife and her lover, Andy begins a new life at the Shawshank prison.",
                    posterPath = "/9cq200qY2DSTqWvPRvJEuRz5ph9.jpg",
                    voteAverage = 8.7,
                    genreIds = listOf(Genre.DRAMA.id, Genre.CRIME.id),
                    releaseDate = "1994-09-23"
                ),
                Movie(
                    id = 27205,
                    title = "Inception",
                    overview = "Cobb, a skilled thief who steals valuable secrets from dream state subconsciousness is given a chance at redemption.",
                    posterPath = "/9gk7adHYeZCE158nnNIBw6qPS2W.jpg",
                    voteAverage = 8.4,
                    genreIds = listOf(Genre.ACTION.id, Genre.SCIENCE_FICTION.id, Genre.ADVENTURE.id, Genre.MYSTERY.id),
                    releaseDate = "2010-07-15"
                )
            )
            Mood.ENERGETIC -> listOf(
                Movie(
                    id = 76341,
                    title = "Mad Max: Fury Road",
                    overview = "An apocalyptic story set in a stark desert landscape where humanity is broken and fighting for survival.",
                    posterPath = "/8tZYrj47qbdfRc695LIgjOVerw9.jpg",
                    voteAverage = 7.6,
                    genreIds = listOf(Genre.ACTION.id, Genre.ADVENTURE.id, Genre.SCIENCE_FICTION.id),
                    releaseDate = "2015-05-13"
                ),
                Movie(
                    id = 324857,
                    title = "Spider-Man: Into the Spider-Verse",
                    overview = "Teen Miles Morales becomes the Spider-Man of his universe and joins forces with spider-heroes from other dimensions.",
                    posterPath = "/iiZZN63i7P2tdzsO7EsxURQLOVg.jpg",
                    voteAverage = 8.4,
                    genreIds = listOf(Genre.ANIMATION.id, Genre.ACTION.id, Genre.ADVENTURE.id, Genre.SCIENCE_FICTION.id),
                    releaseDate = "2018-12-06"
                ),
                Movie(
                    id = 693134,
                    title = "Dune: Part Two",
                    overview = "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen for revenge.",
                    posterPath = "/czemb3hm1Yxmq6dhv56869aXbFB.jpg",
                    voteAverage = 8.2,
                    genreIds = listOf(Genre.SCIENCE_FICTION.id, Genre.ADVENTURE.id),
                    releaseDate = "2024-02-27"
                )
            )
            Mood.DREAMY -> listOf(
                Movie(
                    id = 129,
                    title = "Spirited Away",
                    overview = "A young girl Chihiro becomes trapped in a mysterious spirit world where she must free her parents.",
                    posterPath = "/39wmItIWsgG4m2yCtQs7hAUBvx1.jpg",
                    voteAverage = 8.5,
                    genreIds = listOf(Genre.ANIMATION.id, Genre.FAMILY.id, Genre.FANTASY.id),
                    releaseDate = "2001-07-20"
                ),
                Movie(
                    id = 671,
                    title = "Harry Potter and the Sorcerer's Stone",
                    overview = "Harry discovers he is a wizard and is invited to attend the Hogwarts School.",
                    posterPath = "/wuMc08IPKLI7Hz6Lw234yR6LV2G.jpg",
                    voteAverage = 7.9,
                    genreIds = listOf(Genre.ADVENTURE.id, Genre.FANTASY.id, Genre.FAMILY.id),
                    releaseDate = "2001-11-16"
                ),
                Movie(
                    id = 603,
                    title = "The Matrix",
                    overview = "A computer hacker joins a group of underground insurgents fighting the machines that rule the earth.",
                    posterPath = "/f89U3wzqrjFnHwb9Y9OMv6U0hIh.jpg",
                    voteAverage = 8.2,
                    genreIds = listOf(Genre.ACTION.id, Genre.SCIENCE_FICTION.id),
                    releaseDate = "1999-03-30"
                )
            )
            Mood.RELAXED -> listOf(
                Movie(
                    id = 2062,
                    title = "Ratatouille",
                    overview = "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef.",
                    posterPath = "/npHNjldmTHD65wJLUepuRsJ45eb.jpg",
                    voteAverage = 7.8,
                    genreIds = listOf(Genre.ANIMATION.id, Genre.COMEDY.id, Genre.FAMILY.id, Genre.FANTASY.id),
                    releaseDate = "2007-06-29"
                ),
                Movie(
                    id = 862,
                    title = "Toy Story",
                    overview = "Led by Woody, Andy's toys live happily in his room until Buzz Lightyear arrives.",
                    posterPath = "/uXDfjJbbwU2W2mCRj5URVWP4nEt.jpg",
                    voteAverage = 8.0,
                    genreIds = listOf(Genre.ANIMATION.id, Genre.COMEDY.id, Genre.FAMILY.id),
                    releaseDate = "1995-10-30"
                ),
                Movie(
                    id = 346648,
                    title = "Paddington 2",
                    overview = "Paddington picks up a series of odd jobs to buy the perfect present for Aunt Lucy, only for it to be stolen.",
                    posterPath = "/aE31Ut0I5L4rbc553l12mQ7o19g.jpg",
                    voteAverage = 8.1,
                    genreIds = listOf(Genre.COMEDY.id, Genre.FAMILY.id, Genre.ADVENTURE.id),
                    releaseDate = "2017-11-09"
                )
            )
        }
    }
}
