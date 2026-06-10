package com.example.moovie.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * UI State container for the Search Screen.
 */
sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        _query
            .debounce(500) // Avoid API Spam
            .distinctUntilChanged()
            .onEach { q ->
                if (q.isBlank()) {
                    _uiState.value = SearchUiState.Idle
                } else {
                    performSearch(q)
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Updates the current search query text.
     */
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    /**
     * Retries the search with the current query.
     */
    fun retrySearch() {
        val currentQuery = _query.value
        if (currentQuery.isNotBlank()) {
            performSearch(currentQuery)
        }
    }

    private fun performSearch(queryText: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            movieRepository.searchMovies(queryText)
                .onSuccess { movies ->
                    _uiState.value = SearchUiState.Success(movies)
                }
                .onFailure { error ->
                    Log.e("SearchViewModel", "Search failed for query: $queryText", error)
                    _uiState.value = SearchUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}
