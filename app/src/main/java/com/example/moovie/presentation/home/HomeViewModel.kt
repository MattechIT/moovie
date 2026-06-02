package com.example.moovie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.PreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * State container for the Home Screen Movie Feed.
 */
data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val selectedMood: Mood = Mood.HAPPY,
    val errorMessage: String? = null
)

/**
 * ViewModel managing movie filtering by state of mind and persisting user preferences.
 */
class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadSavedMoodAndFetchMovies()
    }

    /**
     * Restore the last selected mood from DataStore and fetch the corresponding movies.
     */
    private fun loadSavedMoodAndFetchMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val savedMood = preferenceRepository.lastMood.first()
            _uiState.update { it.copy(selectedMood = savedMood) }
            fetchMoviesForMood(savedMood)
        }
    }

    /**
     * Update the active mood with persistence it in DataStore.
     */
    fun selectMood(mood: Mood) {
        if (_uiState.value.selectedMood == mood && _uiState.value.movies.isNotEmpty()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(selectedMood = mood, isLoading = true, errorMessage = null) }
            preferenceRepository.saveLastMood(mood)
            fetchMoviesForMood(mood)
        }
    }

    /**
     * Re-fetch movies for the current mood.
     */
    fun retryFetch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            fetchMoviesForMood(_uiState.value.selectedMood)
        }
    }

    /**
     * Query the repository to load movies.
     */
    private suspend fun fetchMoviesForMood(mood: Mood) {
        movieRepository.getMoviesByMood(mood)
            .onSuccess { movieList ->
                _uiState.update {
                    it.copy(
                        movies = movieList,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        movies = emptyList(),
                        isLoading = false,
                        errorMessage = exception.localizedMessage ?: "Unknown network error"
                    )
                }
            }
    }
}
