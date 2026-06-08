package com.example.moovie.data.repository

import com.example.moovie.data.local.MovieDao
import com.example.moovie.data.local.MovieEntity
import com.example.moovie.data.model.Movie
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class UserMovieRemote(
    val movie_id: Int,
    val is_favorite: Boolean,
    val is_watchlist: Boolean
)

@Serializable
data class UserMovieUpsert(
    val user_id: String,
    val movie_id: Int,
    val is_favorite: Boolean,
    val is_watchlist: Boolean
)

/**
 * Handles remote synchronization of favorite and watchlist movies with Supabase.
 * Decouples room caching from network client operations.
 */
class MovieSyncHandler(
    private val movieDao: MovieDao,
    private val supabaseClient: SupabaseClient,
    private val fetchMovieDetails: suspend (Int) -> Movie?
) {
    private val auth = supabaseClient.auth
    private val postgrest = supabaseClient.postgrest

    private fun getUserId(): String? {
        return auth.currentSessionOrNull()?.user?.id
    }

    init {
        // Observe login/logout and pull remote items
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collectLatest { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        pullMoviesFromRemote()
                    }
                    is SessionStatus.NotAuthenticated -> {
                        clearLocalMovies()
                    }
                    else -> {}
                }
            }
        }
    }

    private suspend fun pullMoviesFromRemote() {
        val userId = getUserId() ?: return
        try {
            val remoteMovies = postgrest["user_movies"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<UserMovieRemote>()

            for (remote in remoteMovies) {
                val existing = movieDao.getMovieById(remote.movie_id)
                if (existing == null) {
                    val movie = fetchMovieDetails(remote.movie_id)
                    if (movie != null) {
                        val newEntity = MovieEntity(
                            id = movie.id,
                            title = movie.title,
                            overview = movie.overview,
                            posterPath = movie.posterPath,
                            backdropPath = movie.backdropPath,
                            voteAverage = movie.voteAverage,
                            releaseDate = movie.releaseDate,
                            runtime = movie.runtime,
                            tagline = movie.tagline,
                            genreIds = movie.combinedGenreIds,
                            isFavorite = remote.is_favorite,
                            isWatchlist = remote.is_watchlist,
                            addedAt = System.currentTimeMillis()
                        )
                        movieDao.insertOrUpdate(newEntity)
                    }
                } else {
                    val updated = existing.copy(
                        isFavorite = remote.is_favorite,
                        isWatchlist = remote.is_watchlist
                    )
                    if (!updated.isFavorite && !updated.isWatchlist) {
                        movieDao.delete(updated)
                    } else {
                        movieDao.insertOrUpdate(updated)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun clearLocalMovies() {
        try {
            // Get current list of favorites and delete them
            movieDao.getFavoriteMovies().first().forEach { movieDao.delete(it) }
            // Get current list of watchlist items and delete them
            movieDao.getWatchlistMovies().first().forEach { movieDao.delete(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncMovieInteraction(movieId: Int, isFavorite: Boolean, isWatchlist: Boolean) {
        val userId = getUserId() ?: return

        // If both states are false, we delete the record from Supabase
        if (!isFavorite && !isWatchlist) {
            try {
                postgrest["user_movies"].delete {
                    filter {
                        eq("user_id", userId)
                        eq("movie_id", movieId)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }

        // Upsert the updated record
        try {
            val record = UserMovieUpsert(
                user_id = userId,
                movie_id = movieId,
                is_favorite = isFavorite,
                is_watchlist = isWatchlist
            )
            postgrest["user_movies"].upsert(record) {
                onConflict = "user_id,movie_id"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
