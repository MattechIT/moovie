package com.example.moovie.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Genre
import com.example.moovie.data.model.Mood
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * UI State representing stats screen content.
 */
data class StatsUiState(
    val totalFavorites: Int = 0,
    val totalWatchlist: Int = 0,
    val totalUniqueSaved: Int = 0,
    val totalWatchTimeMinutes: Int = 0,
    val genreDistribution: List<Pair<Genre, Int>> = emptyList(),
    val moodUsageDistribution: List<Pair<Mood, Int>> = emptyList(),
    val isLoading: Boolean = true
)

/**
 * ViewModel aggregating data from Room (saved movies, watch time, genres)
 * and DataStore (mood usage counters) to build reactive statistics.
 */
class StatsViewModel(
    private val movieRepository: MovieRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> = combine(
        movieRepository.getFavoriteMovies(),
        movieRepository.getWatchlistMovies(),
        preferenceRepository.moodUsageCounts
    ) { favoriteMovies, watchlistMovies, moodCounts ->
        
        // 1. Unique movies saved (avoid counting a movie twice if it's both favorite and watchlist)
        val favIds = favoriteMovies.map { it.id }.toSet()
        val watchIds = watchlistMovies.map { it.id }.toSet()
        val allUniqueIds = favIds union watchIds
        
        val allMoviesMap = (favoriteMovies + watchlistMovies).associateBy { it.id }
        val uniqueMovies = allUniqueIds.mapNotNull { allMoviesMap[it] }
        
        val totalWatchTime = uniqueMovies.sumOf { it.runtime ?: 0 }
        
        // 2. Genres distribution based on saved movies
        val genreCounts = mutableMapOf<Genre, Int>()
        uniqueMovies.forEach { movie ->
            movie.combinedGenreIds.forEach { id ->
                Genre.fromId(id)?.let { genre ->
                    genreCounts[genre] = (genreCounts[genre] ?: 0) + 1
                }
            }
        }
        val sortedGenres = genreCounts.toList().sortedByDescending { it.second }
        
        // 3. Moods usage distribution
        val sortedMoods = moodCounts.toList().sortedByDescending { it.second }

        StatsUiState(
            totalFavorites = favoriteMovies.size,
            totalWatchlist = watchlistMovies.size,
            totalUniqueSaved = uniqueMovies.size,
            totalWatchTimeMinutes = totalWatchTime,
            genreDistribution = sortedGenres,
            moodUsageDistribution = sortedMoods,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState()
    )
}
