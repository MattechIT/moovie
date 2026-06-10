package com.example.moovie.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moovie.data.model.AppLanguage
import com.example.moovie.data.model.AppTheme
import com.example.moovie.data.model.Mood
import io.github.jan.supabase.SupabaseClient
import java.io.File
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Interface defining preference storage operations.
 */
interface PreferenceRepository {
    val lastMood: Flow<Mood>
    suspend fun saveLastMood(mood: Mood)
    val username: Flow<String>
    val bio: Flow<String>
    val avatarUri: Flow<String>
    suspend fun saveUsername(username: String)
    suspend fun saveBio(bio: String)
    suspend fun saveAvatarUri(uri: String): String?
    val appTheme: Flow<AppTheme>
    suspend fun saveAppTheme(theme: AppTheme)
    val appLanguage: Flow<AppLanguage>
    suspend fun saveAppLanguage(language: AppLanguage)
    val moodUsageCounts: Flow<Map<Mood, Int>>
    suspend fun incrementMoodCount(mood: Mood)
    val biometricLockEnabled: Flow<Boolean>
    suspend fun saveBiometricLockEnabled(enabled: Boolean)
}

/**
 * Implementation using Jetpack Preferences DataStore.
 */
class PreferenceRepositoryImpl(
    private val context: Context,
    private val dataStore: DataStore<Preferences>,
    supabaseClient: SupabaseClient
) : PreferenceRepository {

    private val syncHandler = ProfileSyncHandler(context, dataStore, supabaseClient)

    private companion object {
        val KEY_LAST_MOOD = stringPreferencesKey("last_mood")
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_BIO = stringPreferencesKey("bio")
        val KEY_AVATAR_URI = stringPreferencesKey("avatar_uri")
        val KEY_APP_THEME = stringPreferencesKey("app_theme")
        val KEY_APP_LANGUAGE = stringPreferencesKey("app_language")
        val KEY_BIOMETRIC_LOCK_ENABLED = booleanPreferencesKey("biometric_lock_enabled")
    }

    override val lastMood: Flow<Mood> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val moodName = preferences[KEY_LAST_MOOD]
            Mood.fromString(moodName)
        }

    override suspend fun saveLastMood(mood: Mood) {
        dataStore.edit { preferences ->
            preferences[KEY_LAST_MOOD] = mood.name
        }
    }

    override val username: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_USERNAME] ?: ""
        }

    override val bio: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_BIO] ?: ""
        }

    override val avatarUri: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_AVATAR_URI] ?: ""
        }

    override suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = username
        }
        syncHandler.syncUsername(username)
    }

    override suspend fun saveBio(bio: String) {
        dataStore.edit { preferences ->
            preferences[KEY_BIO] = bio
        }
        syncHandler.syncBio(bio)
    }

    override suspend fun saveAvatarUri(uri: String): String? {
        if (uri.isBlank()) {
            dataStore.edit { preferences ->
                preferences[KEY_AVATAR_URI] = ""
            }
            syncHandler.syncAvatar(null)
            return ""
        }
        return try {
            val sourceUri = uri.toUri()
            val inputStream = context.contentResolver.openInputStream(sourceUri) ?: return null
            val avatarFile = File(context.filesDir, "profile_avatar_${System.currentTimeMillis()}.jpg")
            
            context.filesDir.listFiles { _, name ->
                name.startsWith("profile_avatar_") && name.endsWith(".jpg")
            }?.forEach { it.delete() }
            
            avatarFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            
            val savedUri = Uri.fromFile(avatarFile).toString()
            dataStore.edit { preferences ->
                preferences[KEY_AVATAR_URI] = savedUri
            }
            
            // Sync image bytes with remote Supabase storage
            val bytes = avatarFile.readBytes()
            syncHandler.syncAvatar(bytes) ?: savedUri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override val appTheme: Flow<AppTheme> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val themeName = preferences[KEY_APP_THEME]
            AppTheme.fromString(themeName)
        }

    override suspend fun saveAppTheme(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[KEY_APP_THEME] = theme.name
        }
    }

    override val appLanguage: Flow<AppLanguage> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val langName = preferences[KEY_APP_LANGUAGE]
            AppLanguage.fromString(langName)
        }

    override suspend fun saveAppLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[KEY_APP_LANGUAGE] = language.code
        }
    }

    override val biometricLockEnabled: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_BIOMETRIC_LOCK_ENABLED] ?: false
        }

    override suspend fun saveBiometricLockEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_BIOMETRIC_LOCK_ENABLED] = enabled
        }
    }

    override val moodUsageCounts: Flow<Map<Mood, Int>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            Mood.entries.associateWith { mood ->
                preferences[intPreferencesKey("mood_count_${mood.name.lowercase()}")] ?: 0
            }
        }

    override suspend fun incrementMoodCount(mood: Mood) {
        var nextCount = 0
        dataStore.edit { preferences ->
            val key = intPreferencesKey("mood_count_${mood.name.lowercase()}")
            val currentCount = preferences[key] ?: 0
            preferences[key] = currentCount + 1
            nextCount = currentCount + 1
        }
        syncHandler.syncMoodCount(mood.name, nextCount)
    }
}
