package com.example.moovie.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * State container for the Movie Detail Screen.
 */
data class DetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false,
    val isSaved: Boolean = false
)

/**
 * ViewModel managing detailed view state and in-memory love/save actions.
 */
class DetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    /**
     * Fetch movie details by ID from the repository.
     */
    fun loadMovie(movieId: Int) {
        if (_uiState.value.movie?.id == movieId) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            movieRepository.getMovieById(movieId)
                .onSuccess { movieDetails ->
                    _uiState.update {
                        it.copy(
                            movie = movieDetails,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            movie = null,
                            isLoading = false,
                            errorMessage = exception.localizedMessage ?: "Failed to load movie details"
                        )
                    }
                }
        }
    }

    /**
     * Toggle the Favorite status.
     */
    fun toggleFavorite() {
        _uiState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    /**
     * Toggle the Watchlist status.
     */
    fun toggleSaved() {
        _uiState.update { it.copy(isSaved = !it.isSaved) }
    }
}
