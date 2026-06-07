package com.example.moovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.ui.screens.MainShell
import com.example.moovie.ui.theme.MoovieTheme
import org.koin.android.ext.android.inject

/**
 * Entry point of the Moovie application.
 * Dynamically listens to the selected theme setting and applies it globally.
 */
class MainActivity : ComponentActivity() {

    private val preferenceRepository: PreferenceRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Collect theme changes from PreferenceRepository
            val appTheme by preferenceRepository.appTheme.collectAsState(initial = AppTheme.SYSTEM)
            val darkTheme = when (appTheme) {
                AppTheme.SYSTEM -> isSystemInDarkTheme()
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
            }

            MoovieTheme(darkTheme = darkTheme) {
                MainShell()
            }
        }
    }
}