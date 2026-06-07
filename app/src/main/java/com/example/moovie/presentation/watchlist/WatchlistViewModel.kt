package com.example.moovie.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for managing the Movie Watchlist screen.
 * Observes database flows reactively.
 */
class WatchlistViewModel(
    movieRepository: MovieRepository
) : ViewModel() {

    val watchlistMovies: StateFlow<List<Movie>> = movieRepository.getWatchlistMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
