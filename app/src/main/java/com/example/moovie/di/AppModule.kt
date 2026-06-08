package com.example.moovie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.moovie.data.local.AppDatabase
import com.example.moovie.data.repository.AuthRepository
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
import com.example.moovie.presentation.search.SearchViewModel
import com.example.moovie.data.remote.TmdbApiService
import com.example.moovie.data.remote.TmdbApiServiceImpl
import com.example.moovie.BuildConfig
import com.example.moovie.data.repository.SupabaseAuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
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

    // Supabase Client singleton
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Storage)
        }
    }

    // TMDB API Service remote data source
    single<TmdbApiService> { TmdbApiServiceImpl(get()) }

    // Auth repository implementation
    single<AuthRepository> { SupabaseAuthRepository(get()) }
    
    // Preference repository implementation
    single<PreferenceRepository> { PreferenceRepositoryImpl(androidContext(), get(), get()) }

    // Movie repository implementation (uses HttpClient and MovieDao)
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    // LocationService platform service singleton
    single { com.example.moovie.platform.location.LocationService(androidContext()) }

    // ViewModels
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { WatchlistViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { StatsViewModel(get(), get()) }
    viewModel { MovieExplorerViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
}
