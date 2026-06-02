package com.example.moovie.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moovie.data.model.Mood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Interface defining preference storage operations.
 */
interface PreferenceRepository {
    val lastMood: Flow<Mood>
    suspend fun saveLastMood(mood: Mood)
}

/**
 * Implementation using Jetpack Preferences DataStore.
 */
class PreferenceRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {

    private companion object {
        val KEY_LAST_MOOD = stringPreferencesKey("last_mood")
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
}
