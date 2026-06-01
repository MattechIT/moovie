package com.example.moovie.data.repository

import com.example.moovie.data.model.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Mock implementation of [AuthRepository] to simulate local/remote session handling.
 * Useful for offline development and testing before connecting to Firebase.
 */
class MockAuthRepository : AuthRepository {

    private val _sessionState = MutableStateFlow(UserSession())
    override val sessionState: StateFlow<UserSession> = _sessionState.asStateFlow()

    override suspend fun login(email: String, password: String): Result<Unit> {
        // Simulate network latency
        delay(1000)
        
        return if (email.contains("@") && password.length >= 6) {
            _sessionState.update { UserSession(email = email, isAuthenticated = true) }
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid email format or password (min 6 characters)"))
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        // Simulate network latency
        delay(1000)
        
        return if (email.contains("@") && password.length >= 6) {
            _sessionState.update { UserSession(email = email, isAuthenticated = true) }
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Registration failed: check inputs"))
        }
    }

    override suspend fun logout(): Result<Unit> {
        // Simulate network latency
        delay(500)
        _sessionState.update { UserSession(email = null, isAuthenticated = false) }
        return Result.success(Unit)
    }
}
