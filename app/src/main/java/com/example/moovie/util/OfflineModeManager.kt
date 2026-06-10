package com.example.moovie.util

import io.ktor.client.plugins.api.createClientPlugin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException

/**
 * Centralized manager for simulating offline mode.
 * When enabled, all HTTP requests are blocked at the Ktor engine level.
 */
object OfflineModeManager {
    private val _isOfflineMode = MutableStateFlow(false)
    val isOfflineMode: StateFlow<Boolean> = _isOfflineMode.asStateFlow()

    fun setOffline(offline: Boolean) {
        _isOfflineMode.value = offline
    }
}

/**
 * Ktor client plugin that intercepts every outgoing HTTP request.
 * If offline mode is active, throws an [IOException] before the request
 * reaches the network, simulating a connectivity loss.
 */
val OfflineInterceptor = createClientPlugin("OfflineInterceptor") {
    onRequest { _, _ ->
        if (OfflineModeManager.isOfflineMode.value) {
            throw IOException("Simulated offline mode.")
        }
    }
}
