package com.example.moovie.data.repository

import com.example.moovie.data.model.UserSession
import kotlinx.coroutines.flow.StateFlow

/**
 * Agnostic interface for handling user authentication and session management.
 * This contract isolates the UI/ViewModels from different technologies.
 */
interface AuthRepository {
    /**
     * Observable stream representing the live state of user authentication.
     */
    val sessionState: StateFlow<UserSession>
    
    /**
     * Signs in a user using credentials.
     */
    suspend fun login(email: String, password: String): Result<Unit>
    
    /**
     * Creates a new user account.
     */
    suspend fun register(email: String, password: String): Result<Unit>
    
    /**
     * Terminates the current active session.
     */
    suspend fun logout(): Result<Unit>
}
