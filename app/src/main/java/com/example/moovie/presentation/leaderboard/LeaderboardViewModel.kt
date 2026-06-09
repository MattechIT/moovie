package com.example.moovie.presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.LeaderboardUser
import com.example.moovie.data.repository.ProfileSyncHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LeaderboardUiState {
    data object Loading : LeaderboardUiState
    data class Success(val users: List<LeaderboardUser>) : LeaderboardUiState
    data class Error(val message: String) : LeaderboardUiState
}

class LeaderboardViewModel(
    private val profileSyncHandler: ProfileSyncHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow<LeaderboardUiState>(LeaderboardUiState.Loading)
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

    init {
        loadLeaderboard()
    }

    fun loadLeaderboard() {
        viewModelScope.launch {
            _uiState.value = LeaderboardUiState.Loading
            profileSyncHandler.getLeaderboard()
                .onSuccess { users ->
                    _uiState.value = LeaderboardUiState.Success(users)
                }
                .onFailure { error ->
                    _uiState.value = LeaderboardUiState.Error(error.message ?: "Failed to load leaderboard")
                }
        }
    }
}
