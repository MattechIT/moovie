package com.example.moovie.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.repository.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel managing application settings, such as theme preferences.
 */
class SettingsViewModel(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    // Expose the current application theme preference
    val appTheme: StateFlow<AppTheme> = preferenceRepository.appTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppTheme.SYSTEM
        )

    /**
     * Saves the chosen application theme preference.
     */
    fun setAppTheme(theme: AppTheme) {
        viewModelScope.launch {
            preferenceRepository.saveAppTheme(theme)
        }
    }
}
