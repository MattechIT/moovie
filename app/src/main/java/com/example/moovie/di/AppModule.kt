package com.example.moovie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.moovie.data.local.AppDatabase
import com.example.moovie.data.repository.AuthRepository
import com.example.moovie.data.repository.MockAuthRepository
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.MovieRepositoryImpl
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.data.repository.PreferenceRepositoryImpl
import com.example.moovie.presentation.auth.AuthViewModel
import com.example.moovie.presentation.detail.DetailViewModel
import com.example.moovie.presentation.favorites.FavoritesViewModel
import com.example.moovie.presentation.home.HomeViewModel
import com.example.moovie.presentation.profile.ProfileViewModel
import com.example.moovie.presentation.settings.SettingsViewModel
import com.example.moovie.presentation.stats.StatsViewModel
import com.example.moovie.presentation.explorer.MovieExplorerViewModel
import com.example.moovie.presentation.watchlist.WatchlistViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "moovie_preferences")

val appModule = module {
    // AppDatabase Room singleton
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "moovie.db"
        ).build()
    }

    // MovieDao singleton
    single { get<AppDatabase>().movieDao() }

    // DataStore singleton
    single { androidContext().dataStore }

    // Ktor HTTP Client singleton
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }

    // Auth repository implementation
    single<AuthRepository> { MockAuthRepository() }
    
    // Preference repository implementation
    single<PreferenceRepository> { PreferenceRepositoryImpl(androidContext(), get()) }

    // Movie repository implementation (uses HttpClient and MovieDao)
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { WatchlistViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { StatsViewModel(get(), get()) }
    viewModel { MovieExplorerViewModel(get()) }
}
