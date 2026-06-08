package com.example.moovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.moovie.data.model.AppLanguage
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.ui.screens.MainShell
import com.example.moovie.ui.theme.MoovieTheme
import org.koin.android.ext.android.inject

/**
 * Entry point of the Moovie application.
 * Dynamically listens to the selected theme and language settings and applies them globally.
 */
class MainActivity : ComponentActivity() {

    private val preferenceRepository: PreferenceRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Collect theme and language changes from PreferenceRepository
            val appTheme by preferenceRepository.appTheme.collectAsState(initial = AppTheme.SYSTEM)
            val appLanguage by preferenceRepository.appLanguage.collectAsState(initial = AppLanguage.ITALIAN)

            val darkTheme = when (appTheme) {
                AppTheme.SYSTEM -> isSystemInDarkTheme()
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
            }

            // Create a localized context based on the selected language
            val context = androidx.compose.ui.platform.LocalContext.current
            val locale = java.util.Locale.forLanguageTag(appLanguage.code)
            java.util.Locale.setDefault(locale)
            
            val configuration = android.content.res.Configuration(context.resources.configuration)
            configuration.setLocale(locale)
            val localizedContext = context.createConfigurationContext(configuration)

            val activityResultRegistryOwner = androidx.activity.compose.LocalActivityResultRegistryOwner.current ?: this

            CompositionLocalProvider(
                androidx.compose.ui.platform.LocalContext provides localizedContext,
                androidx.activity.compose.LocalActivityResultRegistryOwner provides activityResultRegistryOwner
            ) {
                MoovieTheme(darkTheme = darkTheme) {
                    MainShell()
                }
            }
        }
    }
}