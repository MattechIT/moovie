package com.example.moovie.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * States representing the authentication UI flow.
 */
sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Error(val message: String) : AuthUiState
    data object Success : AuthUiState
}

/**
 * Handles presentation logic for user authentication (Login and Registration).
 * Adheres to classroom guidelines: text fields utilize [mutableStateOf] to prevent cursor jumps.
 */
class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // Exposes the current session state from repository
    val sessionState = authRepository.sessionState

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    /**
     * Executes user login.
     */
    fun login(onSuccess: () -> Unit) {
        if (!validateInputs()) return
        
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.login(email, password)
                .onSuccess {
                    _uiState.value = AuthUiState.Success
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState.Error(error.message ?: "Login failed")
                }
        }
    }

    /**
     * Executes user registration.
     */
    fun register(onSuccess: () -> Unit) {
        if (!validateInputs()) return
        
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.register(email, password)
                .onSuccess {
                    _uiState.value = AuthUiState.Success
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState.Error(error.message ?: "Registration failed")
                }
        }
    }

    /**
     * Resets the UI State back to Idle.
     */
    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    /**
     * Basic client-side validation rules.
     */
    private fun validateInputs(): Boolean {
        return if (!email.contains("@")) {
            _uiState.value = AuthUiState.Error("Invalid email format")
            false
        } else if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters")
            false
        } else {
            true
        }
    }
}
