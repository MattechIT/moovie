package com.example.moovie.data.model

/**
 * Represents the current authentication state of the user.
 */
data class UserSession(
    val email: String? = null,
    val isAuthenticated: Boolean = false,
    val isInitializing: Boolean = true
)
