package com.example.moovie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.moovie.data.repository.AuthRepository
import com.example.moovie.data.repository.MockAuthRepository
import com.example.moovie.data.repository.MovieRepository
import com.example.moovie.data.repository.MovieRepositoryImpl
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.data.repository.PreferenceRepositoryImpl
import com.example.moovie.presentation.auth.AuthViewModel
import com.example.moovie.presentation.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "moovie_preferences")

val appModule = module {
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
    single<PreferenceRepository> { PreferenceRepositoryImpl(get()) }

    // Movie repository implementation
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}
