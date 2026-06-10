package com.example.moovie

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.moovie.data.model.AppLanguage
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.ui.screens.MainShell
import com.example.moovie.ui.theme.MoovieTheme
import java.util.Locale
import org.koin.android.ext.android.inject

val LocalActivity = androidx.compose.runtime.staticCompositionLocalOf<FragmentActivity?> { null }

/**
 * Entry point of the Moovie application.
 * Dynamically listens to the selected theme and language settings and applies them globally.
 */
class MainActivity : FragmentActivity() {

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
            val context = LocalContext.current
            val configuration = LocalConfiguration.current
            val locale = Locale.forLanguageTag(appLanguage.code)
            Locale.setDefault(locale)
            
            val newConfiguration = Configuration(configuration)
            newConfiguration.setLocale(locale)
            val localizedContext = context.createConfigurationContext(newConfiguration)

            val activityResultRegistryOwner = LocalActivityResultRegistryOwner.current ?: this

            val navController = rememberNavController()

            CompositionLocalProvider(
                LocalContext provides localizedContext,
                LocalActivityResultRegistryOwner provides activityResultRegistryOwner,
                LocalActivity provides this
            ) {
                MoovieTheme(darkTheme = darkTheme) {
                    MainShell(navController = navController)
                }
            }
        }
    }
}