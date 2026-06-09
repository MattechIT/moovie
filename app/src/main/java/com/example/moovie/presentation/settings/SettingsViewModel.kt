package com.example.moovie.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.AppLanguage
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel managing application settings, such as theme preferences and user profile updates.
 */
class SettingsViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    // Expose the current application theme preference
    val appTheme: StateFlow<AppTheme> = preferenceRepository.appTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppTheme.SYSTEM
        )

    // Expose the current language
    val appLanguage: StateFlow<AppLanguage> = preferenceRepository.appLanguage
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppLanguage.ITALIAN
        )

    // Expose the biometric lock state
    val biometricLockEnabled: StateFlow<Boolean> = preferenceRepository.biometricLockEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    var usernameInput by mutableStateOf("")
        private set

    var bioInput by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            usernameInput = preferenceRepository.username.first()
            bioInput = preferenceRepository.bio.first()
        }
    }

    fun updateUsername(name: String) {
        usernameInput = name
    }

    fun updateBio(bioText: String) {
        bioInput = bioText
    }

    /**
     * Saves the chosen application theme preference.
     */
    fun setAppTheme(theme: AppTheme) {
        viewModelScope.launch {
            preferenceRepository.saveAppTheme(theme)
        }
    }

    /**
     * Saves the chosen application language preference.
     */
    fun setAppLanguage(language: AppLanguage) {
        viewModelScope.launch {
            preferenceRepository.saveAppLanguage(language)
            try {
                movieRepository.updateSavedMoviesLanguage()
            } catch (_: Exception) {
                // Ignore background translation errors
            }
        }
    }

    /**
     * Saves biometric lock enabled preference.
     */
    fun setBiometricLockEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferenceRepository.saveBiometricLockEnabled(enabled)
        }
    }

    /**
     * Saves username and bio modifications to the datastore.
     */
    fun saveProfile(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            preferenceRepository.saveUsername(usernameInput)
            preferenceRepository.saveBio(bioInput)
            onSuccess()
        }
    }
}
