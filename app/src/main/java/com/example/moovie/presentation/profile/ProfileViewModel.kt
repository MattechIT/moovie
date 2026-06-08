package com.example.moovie.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.repository.AuthRepository
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for user profile screen.
 * Handles inputs without cursor reset issues and aggregates DB counters.
 */
class ProfileViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val movieRepository: MovieRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val username: StateFlow<String> = preferenceRepository.username
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    val bio: StateFlow<String> = preferenceRepository.bio
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    var avatarUriInput by mutableStateOf("")
        private set

    val favoriteCount: StateFlow<Int> = movieRepository.getFavoriteMovies()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val watchlistCount: StateFlow<Int> = movieRepository.getWatchlistMovies()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    init {
        viewModelScope.launch {
            avatarUriInput = preferenceRepository.avatarUri.first()
        }
    }

    fun updateAvatarUri(uri: String) {
        viewModelScope.launch {
            val savedUri = preferenceRepository.saveAvatarUri(uri)
            if (savedUri != null) {
                avatarUriInput = savedUri
            }
        }
    }

    fun logout(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            authRepository.logout()
            onSuccess()
        }
    }
}
