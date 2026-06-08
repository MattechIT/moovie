package com.example.moovie.data.repository

import android.content.Context
import com.example.moovie.R
import com.example.moovie.data.model.UserSession
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Real implementation of [AuthRepository] using Supabase Auth (GoTrue).
 */
class SupabaseAuthRepository(
    private val context: Context,
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    private val auth = supabaseClient.auth
    private val _sessionState = MutableStateFlow(UserSession())
    override val sessionState: StateFlow<UserSession> = _sessionState.asStateFlow()

    init {
        // Observe Supabase auth status reactive changes
        CoroutineScope(Dispatchers.Default).launch {
            auth.sessionStatus.collectLatest { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        _sessionState.update {
                            UserSession(
                                email = status.session.user?.email,
                                isAuthenticated = true
                            )
                        }
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _sessionState.update {
                            UserSession(
                                email = null,
                                isAuthenticated = false
                            )
                        }
                    }
                    else -> {
                        // Keep current state
                    }
                }
            }
        }
    }

    private fun mapAuthException(e: Exception): Exception {
        val message = e.message ?: ""
        return when {
            message.contains("invalid_credentials", ignoreCase = true) ->
                Exception(context.getString(R.string.auth_invalid_credentials))
            message.contains("email_not_confirmed", ignoreCase = true) ->
                Exception(context.getString(R.string.auth_email_not_confirmed))
            message.contains("user_already_exists", ignoreCase = true) ->
                Exception(context.getString(R.string.auth_user_already_exists))
            message.contains("network", ignoreCase = true) || message.contains("connect", ignoreCase = true) ->
                Exception(context.getString(R.string.auth_network_error))
            else -> Exception(context.getString(R.string.auth_unknown_error))
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapAuthException(e))
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapAuthException(e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
