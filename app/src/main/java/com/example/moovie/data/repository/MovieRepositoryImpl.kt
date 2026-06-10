package com.example.moovie.data.repository

import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import com.example.moovie.data.model.Credits
import com.example.moovie.data.model.CrewMember
import com.example.moovie.data.local.MovieDao
import com.example.moovie.data.local.MovieEntity
import com.example.moovie.data.remote.TmdbApiService
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of MovieRepository which fetches movies from the TMDB remote API
 * using [TmdbApiService], with a robust fallback to a high-fidelity mock catalog when offline or key is missing.
 */
class MovieRepositoryImpl(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val supabaseClient: SupabaseClient
) : MovieRepository {

    private val syncHandler = MovieSyncHandler(
        movieDao = movieDao,
        supabaseClient = supabaseClient,
        fetchMovieDetails = { movieId -> getMovieById(movieId).getOrNull() }
    )

    override suspend fun getMoviesByMood(mood: Mood, page: Int): Result<List<Movie>> {
        if (!tmdbApiService.isApiKeyConfigured()) {
            // Automatically fall back to the separated mock catalog only on page 1
            return if (page == 1) {
                Result.success(MockMovieCatalog.getMockMoviesForMood(mood))
            } else {
                Result.success(emptyList())
            }
        }
        return try {
            val genresParam = mood.genres.map { it.id }.joinToString(",")
            val movies = tmdbApiService.getMoviesByGenres(genresParam, page)
            Result.success(movies)
        } catch (_: Exception) {
            // Gracefully fall back to local high-fidelity mock catalog only on page 1
            if (page == 1) {
                Result.success(MockMovieCatalog.getMockMoviesForMood(mood))
            } else {
                Result.success(emptyList())
            }
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        if (!tmdbApiService.isApiKeyConfigured()) {
            val mockMovie = MockMovieCatalog.getMockMovieById(movieId)
            return if (mockMovie != null) {
                Result.success(mockMovie)
            } else {
                Result.failure(Exception("Movie not found in mock catalog"))
            }
        }
        return try {
            val movie = tmdbApiService.getMovieById(movieId)
            Result.success(movie)
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
        val nextFavorite: Boolean
        val nextWatchlist: Boolean
        if (existing == null) {
            val newEntity = movie.toEntity(isFavorite = true, isWatchlist = false)
            movieDao.insertOrUpdate(newEntity)
            nextFavorite = true
            nextWatchlist = false
        } else {
            val updated = existing.copy(
                isFavorite = !existing.isFavorite,
                addedAt = System.currentTimeMillis()
            )
            nextFavorite = updated.isFavorite
            nextWatchlist = updated.isWatchlist
            if (!updated.isFavorite && !updated.isWatchlist) {
                movieDao.delete(updated)
            } else {
                movieDao.insertOrUpdate(updated)
            }
        }
        syncHandler.syncMovieInteraction(movie.id, nextFavorite, nextWatchlist)
    }

    override suspend fun toggleWatchlist(movie: Movie) {
        val existing = movieDao.getMovieById(movie.id)
        val nextFavorite: Boolean
        val nextWatchlist: Boolean
        if (existing == null) {
            val newEntity = movie.toEntity(isFavorite = false, isWatchlist = true)
            movieDao.insertOrUpdate(newEntity)
            nextFavorite = false
            nextWatchlist = true
        } else {
            val updated = existing.copy(
                isWatchlist = !existing.isWatchlist,
                addedAt = System.currentTimeMillis()
            )
            nextFavorite = updated.isFavorite
            nextWatchlist = updated.isWatchlist
            if (!updated.isFavorite && !updated.isWatchlist) {
                movieDao.delete(updated)
            } else {
                movieDao.insertOrUpdate(updated)
            }
        }
        syncHandler.syncMovieInteraction(movie.id, nextFavorite, nextWatchlist)
    }

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        if (!tmdbApiService.isApiKeyConfigured()) {
            return Result.success(MockMovieCatalog.searchMockMovies(query))
        }
        return try {
            val movies = tmdbApiService.searchMovies(query)
            Result.success(movies)
        } catch (_: Exception) {
            Result.success(MockMovieCatalog.searchMockMovies(query))
        }
    }

    override suspend fun getMoviesByActor(actorId: Int, page: Int): Result<List<Movie>> {
        if (!tmdbApiService.isApiKeyConfigured()) {
            return if (page == 1) {
                val mockMovies = Mood.entries.flatMap { MockMovieCatalog.getMockMoviesForMood(it) }
                    .distinctBy { it.id }
                    .filter { movie ->
                        movie.credits?.cast?.any { it.id == actorId } == true
                    }
                Result.success(mockMovies.ifEmpty { Mood.entries.flatMap { MockMovieCatalog.getMockMoviesForMood(it) }.distinctBy { it.id } })
            } else {
                Result.success(emptyList())
            }
        }
        return try {
            val movies = tmdbApiService.getMoviesByActor(actorId, page)
            Result.success(movies)
        } catch (_: Exception) {
            if (page == 1) {
                val mockMovies = Mood.entries.flatMap { MockMovieCatalog.getMockMoviesForMood(it) }
                    .distinctBy { it.id }
                    .filter { movie ->
                        movie.credits?.cast?.any { it.id == actorId } == true
                    }
                Result.success(mockMovies.ifEmpty { Mood.entries.flatMap { MockMovieCatalog.getMockMoviesForMood(it) }.distinctBy { it.id } })
            } else {
                Result.success(emptyList())
            }
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
        genreIds = genreIds,
        credits = Credits(
            cast = cast ?: emptyList(),
            crew = director?.let { listOf(CrewMember(id = 0, name = it, job = "Director", department = "Directing")) } ?: emptyList()
        )
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
        isWatchlist = isWatchlist,
        director = credits?.crew?.firstOrNull { it.job == "Director" }?.name,
        cast = credits?.cast
    )

    override suspend fun updateSavedMoviesLanguage() {
        val savedMovies = movieDao.getAllMovies()
        savedMovies.forEach { entity ->
            val updatedMovieResult = getMovieById(entity.id)
            updatedMovieResult.onSuccess { updatedMovie ->
                val updatedEntity = entity.copy(
                    title = updatedMovie.title,
                    overview = updatedMovie.overview,
                    tagline = updatedMovie.tagline,
                    posterPath = updatedMovie.posterPath,
                    backdropPath = updatedMovie.backdropPath,
                    runtime = updatedMovie.runtime,
                    genreIds = updatedMovie.combinedGenreIds
                )
                movieDao.insertOrUpdate(updatedEntity)
            }
        }
    }
}

