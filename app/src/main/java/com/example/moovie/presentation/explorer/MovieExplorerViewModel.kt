package com.example.moovie.presentation.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.Cinema
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.MovieRepository
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
    val isLoading: Boolean = false
)

/**
 * ViewModel managing the interactive cinema explorer.
 */
class MovieExplorerViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieExplorerUiState())
    val uiState: StateFlow<MovieExplorerUiState> = _uiState.asStateFlow()

    init {
        loadCinemasAndMovies()
    }

    /**
     * Set the geolocation permission state dynamically.
     */
    fun setPermissionGranted(granted: Boolean) {
        _uiState.update { it.copy(userLocationPermissionGranted = granted) }
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
            val allLoadedMovies = happyMovies + energeticMovies
            
            // Mock Cinemas
            val mockCinemas = listOf(
                Cinema(
                    id = 1,
                    name = "CinemaCity Ravenna",
                    latitude = 44.4180,
                    longitude = 12.1713,
                    address = "Via Secondo Bini, 7, Ravenna",
                    showtimes = listOf("16:00", "18:30", "21:00"),
                    movieIds = happyMovies.map { it.id }
                ),
                Cinema(
                    id = 2,
                    name = "UCI Cinemas Romagna",
                    latitude = 44.1664,
                    longitude = 12.4281,
                    address = "Piazza Fratelli Lumière, 22, Savignano sul Rubicone",
                    showtimes = listOf("17:30", "20:00", "22:30"),
                    movieIds = energeticMovies.map { it.id }
                ),
                Cinema(
                    id = 3,
                    name = "Cinema Eliseo Cesena",
                    latitude = 44.1395,
                    longitude = 12.2468,
                    address = "Viale Giosuè Carducci, 7, Cesena",
                    showtimes = listOf("15:00", "18:00", "21:00"),
                    movieIds = allLoadedMovies.map { it.id }.shuffled().take(2)
                ),
                Cinema(
                    id = 4,
                    name = "Multisala Astoria Forlì",
                    latitude = 44.2057,
                    longitude = 12.0461,
                    address = "Viale dell'Appennino, 313, Forlì",
                    showtimes = listOf("16:30", "19:00", "21:30"),
                    movieIds = allLoadedMovies.map { it.id }.shuffled().take(3)
                )
            )
            
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
