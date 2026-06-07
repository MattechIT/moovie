package com.example.moovie.data.repository

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moovie.data.model.Mood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.IOException

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
}

/**
 * Implementation using Jetpack Preferences DataStore.
 */
class PreferenceRepositoryImpl(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {

    private companion object {
        val KEY_LAST_MOOD = stringPreferencesKey("last_mood")
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_BIO = stringPreferencesKey("bio")
        val KEY_AVATAR_URI = stringPreferencesKey("avatar_uri")
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
    }

    override suspend fun saveBio(bio: String) {
        dataStore.edit { preferences ->
            preferences[KEY_BIO] = bio
        }
    }

    override suspend fun saveAvatarUri(uri: String): String? {
        if (uri.isBlank()) {
            dataStore.edit { preferences ->
                preferences[KEY_AVATAR_URI] = ""
            }
            return ""
        }
        return try {
            val sourceUri = Uri.parse(uri)
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
            savedUri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
