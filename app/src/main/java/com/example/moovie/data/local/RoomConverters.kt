package com.example.moovie.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Room TypeConverters using kotlinx.serialization to persist lists of integers.
 */
class RoomConverters {
    @TypeConverter
    fun fromIntList(list: List<Int>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return Json.decodeFromString(data)
    }
}
