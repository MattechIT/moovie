package com.example.moovie.data.model

import kotlinx.serialization.Serializable

/**
 * Domain model representing a Cinema on the map.
 */
@Serializable
data class Cinema(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val showtimes: List<String>,
    val movieIds: List<Int>
)
