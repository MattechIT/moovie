package com.example.moovie.data.repository

import android.content.Context
import android.net.Uri
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.io.File

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
    private val context: Context,
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
        val KEY_REMOTE_AVATAR_URL = stringPreferencesKey("remote_avatar_url")
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
                val remoteAvatarUrl = profile.avatar_url ?: ""
                val currentRemoteUrl = dataStore.data.first()[KEY_REMOTE_AVATAR_URL] ?: ""
                val currentLocalUriStr = dataStore.data.first()[KEY_AVATAR_URI] ?: ""

                // Check if local file exists
                val localFileExists = if (currentLocalUriStr.isNotBlank()) {
                    try {
                        val pathOnly = currentLocalUriStr.removePrefix("file://").removePrefix("content://")
                        val file = File(pathOnly)
                        file.exists()
                    } catch (_: Exception) {
                        false
                    }
                } else {
                    false
                }

                var finalLocalUri = currentLocalUriStr
                var finalRemoteUrl = currentRemoteUrl

                if (remoteAvatarUrl.isNotBlank()) {
                    if (remoteAvatarUrl != currentRemoteUrl || !localFileExists) {
                        try {
                            val path = "$userId/avatar.jpg"
                            val bytes = storage.from("avatars").downloadAuthenticated(path)

                            // Delete any old avatar files first
                            context.filesDir.listFiles { _, name ->
                                name.startsWith("profile_avatar_") && name.endsWith(".jpg")
                            }?.forEach { it.delete() }

                            // Save new file
                            val avatarFile = File(context.filesDir, "profile_avatar_${System.currentTimeMillis()}.jpg")
                            avatarFile.writeBytes(bytes)

                            finalLocalUri = Uri.fromFile(avatarFile).toString()
                            finalRemoteUrl = remoteAvatarUrl
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    // Remote profile has no avatar, delete local file if it exists
                    try {
                        context.filesDir.listFiles { _, name ->
                            name.startsWith("profile_avatar_") && name.endsWith(".jpg")
                        }?.forEach { it.delete() }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finalLocalUri = ""
                    finalRemoteUrl = ""
                }

                dataStore.edit { preferences ->
                    preferences[KEY_USERNAME] = profile.username ?: ""
                    preferences[KEY_BIO] = profile.bio ?: ""
                    preferences[KEY_AVATAR_URI] = finalLocalUri
                    preferences[KEY_REMOTE_AVATAR_URL] = finalRemoteUrl
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
        try {
            context.filesDir.listFiles { _, name ->
                name.startsWith("profile_avatar_") && name.endsWith(".jpg")
            }?.forEach { it.delete() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        dataStore.edit { preferences ->
            preferences[KEY_USERNAME] = ""
            preferences[KEY_BIO] = ""
            preferences[KEY_AVATAR_URI] = ""
            preferences[KEY_REMOTE_AVATAR_URL] = ""
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
            dataStore.edit { preferences ->
                preferences[KEY_REMOTE_AVATAR_URL] = ""
            }
            return ""
        }

        return try {
            val path = "$userId/avatar.jpg"
            storage.from("avatars").upload(path, bytes) {
                upsert = true
            }
            val publicUrl = storage.from("avatars").publicUrl(path)
            val separator = if (publicUrl.contains("?")) "&" else "?"
            val timestampedUrl = "$publicUrl${separator}t=${System.currentTimeMillis()}"
            
            // Update remote profile with the timestamped URL so other devices detect changes
            postgrest["profiles"].update({
                set("avatar_url", timestampedUrl)
            }) {
                filter {
                    eq("id", userId)
                }
            }
            
            // Save the remote URL locally so we know we are in sync
            dataStore.edit { preferences ->
                preferences[KEY_REMOTE_AVATAR_URL] = timestampedUrl
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
