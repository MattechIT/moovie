package com.example.moovie.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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

/**
 * Handles remote profile synchronization with Supabase (Database & Storage).
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
                    }
                    is SessionStatus.NotAuthenticated -> {
                        clearLocalProfile()
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
                    preferences[KEY_AVATAR_URI] = profile.avatar_url ?: ""
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
            
            // Save public URL locally so it matches remote
            dataStore.edit { preferences ->
                preferences[KEY_AVATAR_URI] = publicUrl
            }
            publicUrl
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
