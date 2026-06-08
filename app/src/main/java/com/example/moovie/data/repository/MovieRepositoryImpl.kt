package com.example.moovie.data.repository

import com.example.moovie.BuildConfig
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.moovie.data.local.MovieDao
import com.example.moovie.data.local.MovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API response container matching TMDB discover response structure.
 */
@Serializable
private data class TmdbResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

/**
 * Implementation of MovieRepository which fetches movies from the TMDB remote API
 * using Ktor Client, with a robust fallback to a high-fidelity mock catalog when offline or key is missing.
 */
class MovieRepositoryImpl(
    private val httpClient: HttpClient,
    private val movieDao: MovieDao
) : MovieRepository {


    private companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    override suspend fun getMoviesByMood(mood: Mood): Result<List<Movie>> {
        if (API_KEY.isBlank() || API_KEY.startsWith("YOUR_TMDB")) {
            // Automatically fall back to the separated mock catalog if no key is configured
            return Result.success(MockMovieCatalog.getMockMoviesForMood(mood))
        }
        return try {
            val genresParam = mood.genres.map { it.id }.joinToString(",")
            val response = httpClient.get("https://api.themoviedb.org/3/discover/movie") {
                parameter("api_key", API_KEY)
                parameter("with_genres", genresParam)
                parameter("language", "it-IT")
                parameter("sort_by", "popularity.desc")
                parameter("include_adult", "false")
                parameter("vote_count.gte", "300") // Filter out obscure or low-voted entries
                parameter("with_original_language", "en|it|fr|es") // Restrict to mainstream Western/European/Italian cinema
                parameter("page", "1")
            }.body<TmdbResponse>()

            Result.success(response.results)
        } catch (_: Exception) {
            // Gracefully fall back to local high-fidelity mock catalog in case of network or key error
            Result.success(MockMovieCatalog.getMockMoviesForMood(mood))
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        if (API_KEY.isBlank() || API_KEY.startsWith("YOUR_TMDB")) {
            val mockMovie = MockMovieCatalog.getMockMovieById(movieId)
            return if (mockMovie != null) {
                Result.success(mockMovie)
            } else {
                Result.failure(Exception("Movie not found in mock catalog"))
            }
        }
        return try {
            val response = httpClient.get("https://api.themoviedb.org/3/movie/$movieId") {
                parameter("api_key", API_KEY)
                parameter("language", "it-IT")
            }.body<Movie>()

            Result.success(response)
        } catch (e: Exception) {
            val mockMovie = MockMovieCatalog.getMockMovieById(movieId)
            if (mockMovie != null) {
                Result.success(mockMovie)
            } else {
                Result.failure(e)
            }
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWatchlistMovies(): Flow<List<Movie>> {
        return movieDao.getWatchlistMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return movieDao.getMovieFlowById(movieId).map { it?.isFavorite ?: false }
    }

    override fun isWatchlisted(movieId: Int): Flow<Boolean> {
        return movieDao.getMovieFlowById(movieId).map { it?.isWatchlist ?: false }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        val existing = movieDao.getMovieById(movie.id)
        if (existing == null) {
            val newEntity = movie.toEntity(isFavorite = true, isWatchlist = false)
            movieDao.insertOrUpdate(newEntity)
        } else {
            val updated = existing.copy(
                isFavorite = !existing.isFavorite,
                addedAt = System.currentTimeMillis()
            )
            if (!updated.isFavorite && !updated.isWatchlist) {
                movieDao.delete(updated)
            } else {
                movieDao.insertOrUpdate(updated)
            }
        }
    }

    override suspend fun toggleWatchlist(movie: Movie) {
        val existing = movieDao.getMovieById(movie.id)
        if (existing == null) {
            val newEntity = movie.toEntity(isFavorite = false, isWatchlist = true)
            movieDao.insertOrUpdate(newEntity)
        } else {
            val updated = existing.copy(
                isWatchlist = !existing.isWatchlist,
                addedAt = System.currentTimeMillis()
            )
            if (!updated.isFavorite && !updated.isWatchlist) {
                movieDao.delete(updated)
            } else {
                movieDao.insertOrUpdate(updated)
            }
        }
    }

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        if (API_KEY.isBlank() || API_KEY.startsWith("YOUR_TMDB")) {
            return Result.success(MockMovieCatalog.searchMockMovies(query))
        }
        return try {
            val response = httpClient.get("https://api.themoviedb.org/3/search/movie") {
                parameter("api_key", API_KEY)
                parameter("query", query)
                parameter("language", "it-IT")
                parameter("include_adult", "false")
                parameter("page", "1")
            }.body<TmdbResponse>()

            Result.success(response.results)
        } catch (_: Exception) {
            Result.success(MockMovieCatalog.searchMockMovies(query))
        }
    }

    private fun MovieEntity.toDomain() = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        runtime = runtime,
        tagline = tagline,
        genreIds = genreIds
    )

    private fun Movie.toEntity(isFavorite: Boolean, isWatchlist: Boolean) = MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        runtime = runtime,
        tagline = tagline,
        genreIds = combinedGenreIds,
        isFavorite = isFavorite,
        isWatchlist = isWatchlist
    )
}

