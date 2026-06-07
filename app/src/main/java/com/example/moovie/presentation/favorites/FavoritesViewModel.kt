package com.example.moovie.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for managing the Favorite Movies screen.
 * Observes database flows reactively.
 */
class FavoritesViewModel(
    movieRepository: MovieRepository
) : ViewModel() {

    val favoriteMovies: StateFlow<List<Movie>> = movieRepository.getFavoriteMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
