package com.example.moovie.presentation.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Cinema
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MockCinemaCatalog
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.platform.location.Coordinates
import com.example.moovie.platform.location.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI State for the Movie Explorer Screen.
 */
data class MovieExplorerUiState(
    val cinemas: List<Cinema> = emptyList(),
    val selectedCinema: Cinema? = null,
    val selectedCinemaMovies: List<Movie> = emptyList(),
    val userLocationPermissionGranted: Boolean = false,
    val userLocation: Coordinates? = null,
    val isGpsWarningDismissed: Boolean = false,
    val isLoading: Boolean = false
)

/**
 * ViewModel managing the interactive cinema explorer.
 */
class MovieExplorerViewModel(
    private val movieRepository: MovieRepository,
    private val locationService: LocationService
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieExplorerUiState())
    val uiState: StateFlow<MovieExplorerUiState> = _uiState.asStateFlow()

    init {
        loadCinemasAndMovies()
    }

    /**
     * Set the geolocation permission state dynamically and fetch coordinates if granted.
     */
    fun setPermissionGranted(granted: Boolean) {
        _uiState.update { it.copy(userLocationPermissionGranted = granted) }
        if (granted) {
            fetchUserLocation()
        }
    }

    /**
     * Asynchronously query coordinates from location service.
     */
    fun fetchUserLocation() {
        viewModelScope.launch {
            val coords = locationService.getCurrentLocation()
            _uiState.update { 
                it.copy(
                    userLocation = coords,
                    userLocationPermissionGranted = if (coords != null) true else it.userLocationPermissionGranted
                )
            }
        }
    }

    /**
     * Dismiss the GPS warning banner manually.
     */
    fun dismissGpsWarning() {
        _uiState.update { it.copy(isGpsWarningDismissed = true) }
    }

    /**
     * Load mock cinemas and query dynamic movie projections from the repository.
     */
    private fun loadCinemasAndMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Retrieve dynamic movies to show in theaters
            val happyResult = movieRepository.getMoviesByMood(Mood.HAPPY)
            val energeticResult = movieRepository.getMoviesByMood(Mood.ENERGETIC)
            
            val happyMovies = happyResult.getOrDefault(emptyList()).take(3)
            val energeticMovies = energeticResult.getOrDefault(emptyList()).take(3)
            
            val happyIds = happyMovies.map { it.id }
            val energeticIds = energeticMovies.map { it.id }
            val mockCinemas = MockCinemaCatalog.getMockCinemas(happyIds, energeticIds)
            
            _uiState.update {
                it.copy(
                    cinemas = mockCinemas,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Select a cinema
     */
    fun selectCinema(cinema: Cinema?) {
        viewModelScope.launch {
            if (cinema == null) {
                _uiState.update { it.copy(selectedCinema = null, selectedCinemaMovies = emptyList()) }
                return@launch
            }
            
            _uiState.update { it.copy(isLoading = true) }
            val resolvedMovies = mutableListOf<Movie>()
            
            cinema.movieIds.forEach { id ->
                movieRepository.getMovieById(id).onSuccess { movie ->
                    resolvedMovies.add(movie)
                }
            }
            
            _uiState.update {
                it.copy(
                    selectedCinema = cinema,
                    selectedCinemaMovies = resolvedMovies,
                    isLoading = false
                )
            }
        }
    }
}
