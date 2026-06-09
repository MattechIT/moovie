package com.example.moovie.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.moovie.data.model.Mood
import com.example.moovie.data.model.LeaderboardUser
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRemote(
    val username: String? = null,
    val bio: String? = null,
    val avatar_url: String? = null
)

@Serializable
data class UserMoodRemote(
    val mood_name: String,
    val count: Int
)

@Serializable
data class UserMoodUpsert(
    val user_id: String,
    val mood_name: String,
    val count: Int
)

/**
 * Handles remote profile and mood statistic synchronization with Supabase.
 * Decouples network/sync logic from the local [PreferenceRepository].
 */
class ProfileSyncHandler(
    private val dataStore: DataStore<Preferences>,
    private val supabaseClient: SupabaseClient
) {
    private val auth = supabaseClient.auth
    private val postgrest = supabaseClient.postgrest
    private val storage = supabaseClient.storage

    private companion object {
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_BIO = stringPreferencesKey("bio")
        val KEY_AVATAR_URI = stringPreferencesKey("avatar_uri")
    }

    private fun getUserId(): String? {
        return auth.currentSessionOrNull()?.user?.id
    }

    init {
        // Automatically sync when user logs in
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collectLatest { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        fetchProfileFromRemote()
                        fetchMoodCountsFromRemote()
                    }
                    is SessionStatus.NotAuthenticated -> {
                        clearLocalProfile()
                        clearLocalMoodCounts()
                    }
                    else -> {}
                }
            }
        }
    }

    private suspend fun fetchProfileFromRemote() {
        val userId = getUserId() ?: return
        try {
            val profile = postgrest["profiles"]
                .select {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingleOrNull<ProfileRemote>()

            if (profile != null) {
                dataStore.edit { preferences ->
                    preferences[KEY_USERNAME] = profile.username ?: ""
                    preferences[KEY_BIO] = profile.bio ?: ""
                    val remoteAvatarUrl = profile.avatar_url ?: ""
                    preferences[KEY_AVATAR_URI] = if (remoteAvatarUrl.isNotBlank()) {
                        val separator = if (remoteAvatarUrl.contains("?")) "&" else "?"
                        "$remoteAvatarUrl${separator}t=${System.currentTimeMillis()}"
                    } else {
                        ""
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchMoodCountsFromRemote() {
        val userId = getUserId() ?: return
        try {
            val remoteMoods = postgrest["user_moods"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<UserMoodRemote>()

            dataStore.edit { preferences ->
                for (remote in remoteMoods) {
                    val key = intPreferencesKey("mood_count_${remote.mood_name.lowercase()}")
                    preferences[key] = remote.count
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun clearLocalProfile() {
        dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = ""
            preferences[KEY_BIO] = ""
            preferences[KEY_AVATAR_URI] = ""
        }
    }

    private suspend fun clearLocalMoodCounts() {
        dataStore.edit { preferences ->
            Mood.entries.forEach { mood ->
                val key = intPreferencesKey("mood_count_${mood.name.lowercase()}")
                preferences[key] = 0
            }
        }
    }

    suspend fun syncUsername(username: String) {
        val userId = getUserId() ?: return
        try {
            postgrest["profiles"].update({
                set("username", username)
            }) {
                filter {
                    eq("id", userId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncBio(bio: String) {
        val userId = getUserId() ?: return
        try {
            postgrest["profiles"].update({
                set("bio", bio)
            }) {
                filter {
                    eq("id", userId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun syncAvatar(bytes: ByteArray?): String? {
        val userId = getUserId() ?: return null
        
        // If null clear remote avatar_url
        if (bytes == null) {
            try {
                postgrest["profiles"].update({
                    set("avatar_url", "")
                }) {
                    filter {
                        eq("id", userId)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        return try {
            val path = "$userId/avatar.jpg"
            storage.from("avatars").upload(path, bytes) {
                upsert = true
            }
            val publicUrl = storage.from("avatars").publicUrl(path)
            
            // Update remote profile with public URL
            postgrest["profiles"].update({
                set("avatar_url", publicUrl)
            }) {
                filter {
                    eq("id", userId)
                }
            }
            
            // Save public URL locally with a cache buster so Coil reloads it
            val separator = if (publicUrl.contains("?")) "&" else "?"
            val timestampedUrl = "$publicUrl${separator}t=${System.currentTimeMillis()}"
            dataStore.edit { preferences ->
                preferences[KEY_AVATAR_URI] = timestampedUrl
            }
            timestampedUrl
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun syncMoodCount(moodName: String, count: Int) {
        val userId = getUserId() ?: return
        try {
            val record = UserMoodUpsert(
                user_id = userId,
                mood_name = moodName,
                count = count
            )
            postgrest["user_moods"].upsert(record) {
                onConflict = "user_id,mood_name"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getLeaderboard(): Result<List<LeaderboardUser>> {
        return try {
            val list = postgrest["profiles"]
                .select {
                    order(column = "movies_count", order = io.github.jan.supabase.postgrest.query.Order.DESCENDING)
                    limit(count = 50)
                }
                .decodeList<LeaderboardUser>()
            Result.success(list)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
