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
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 1,
    val hasReachedEnd: Boolean = false,
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
            _uiState.update {
                it.copy(
                    isLoading = true,
                    movies = emptyList(),
                    currentPage = 1,
                    hasReachedEnd = false
                )
            }
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
            _uiState.update {
                it.copy(
                    selectedMood = mood,
                    isLoading = true,
                    movies = emptyList(),
                    currentPage = 1,
                    hasReachedEnd = false,
                    errorMessage = null
                )
            }
            preferenceRepository.saveLastMood(mood)
            preferenceRepository.incrementMoodCount(mood)
            fetchMoviesForMood(mood)
        }
    }

    /**
     * Re-fetch movies for the current mood.
     */
    fun retryFetch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    movies = emptyList(),
                    currentPage = 1,
                    hasReachedEnd = false,
                    errorMessage = null
                )
            }
            fetchMoviesForMood(_uiState.value.selectedMood)
        }
    }

    /**
     * Query the repository to load movies.
     */
    private suspend fun fetchMoviesForMood(mood: Mood) {
        movieRepository.getMoviesByMood(mood, page = 1)
            .onSuccess { movieList ->
                _uiState.update {
                    it.copy(
                        movies = movieList,
                        isLoading = false,
                        currentPage = 1,
                        hasReachedEnd = movieList.isEmpty() || movieList.size < 20,
                        errorMessage = null
                    )
                }
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        movies = emptyList(),
                        isLoading = false,
                        currentPage = 1,
                        hasReachedEnd = true,
                        errorMessage = exception.localizedMessage ?: "Unknown network error"
                    )
                }
            }
    }

    /**
     * Load the next page of movies for the currently selected mood.
     */
    fun loadNextPage() {
        val currentState = _uiState.value
        if (currentState.isLoading || currentState.isLoadingMore || currentState.hasReachedEnd) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            val nextPage = currentState.currentPage + 1
            movieRepository.getMoviesByMood(currentState.selectedMood, page = nextPage)
                .onSuccess { newMovies ->
                    _uiState.update {
                        it.copy(
                            movies = it.movies + newMovies,
                            isLoadingMore = false,
                            currentPage = nextPage,
                            hasReachedEnd = newMovies.isEmpty() || newMovies.size < 20
                        )
                    }
                }
                .onFailure { _ ->
                    _uiState.update { it.copy(isLoadingMore = false) }
                }
        }
    }
}
