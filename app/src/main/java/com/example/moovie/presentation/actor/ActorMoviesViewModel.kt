package com.example.moovie.presentation.actor

import android.util.Log
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
 * State container for the Actor Movies Screen.
 */
data class ActorMoviesUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 1,
    val hasReachedEnd: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel managing movie loading and pagination for a specific actor.
 */
class ActorMoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActorMoviesUiState())
    val uiState: StateFlow<ActorMoviesUiState> = _uiState.asStateFlow()

    /**
     * Load the initial page of movies for the actor.
     */
    fun loadMovies(actorId: Int) {
        if (_uiState.value.movies.isNotEmpty()) return

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
            fetchMovies(actorId)
        }
    }

    /**
     * Retry loading movies.
     */
    fun retryFetch(actorId: Int) {
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
            fetchMovies(actorId)
        }
    }

    private suspend fun fetchMovies(actorId: Int) {
        movieRepository.getMoviesByActor(actorId, page = 1)
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
                Log.e("ActorMoviesViewModel", "Failed to fetch actor's movies", exception)
                _uiState.update {
                    it.copy(
                        movies = emptyList(),
                        isLoading = false,
                        currentPage = 1,
                        hasReachedEnd = true,
                        errorMessage = exception.localizedMessage ?: "Failed to load actor's movies"
                    )
                }
            }
    }

    /**
     * Load the next page of movies for the actor.
     */
    fun loadNextPage(actorId: Int) {
        val currentState = _uiState.value
        if (currentState.isLoading || currentState.isLoadingMore || currentState.hasReachedEnd) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            val nextPage = currentState.currentPage + 1
            movieRepository.getMoviesByActor(actorId, page = nextPage)
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
