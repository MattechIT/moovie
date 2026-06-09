package com.example.moovie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardUser(
    val id: String,
    val username: String? = null,
    val bio: String? = null,
    val avatar_url: String? = null,
    val movies_count: Int = 0
)
