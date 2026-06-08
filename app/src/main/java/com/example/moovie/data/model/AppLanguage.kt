package com.example.moovie.data.model

/**
 * Supported application languages.
 */
enum class AppLanguage(val code: String, val displayName: String) {
    ITALIAN("it", "Italiano"),
    ENGLISH("en", "English");

    companion object {
        fun fromString(value: String?): AppLanguage {
            return entries.firstOrNull { it.code == value || it.name == value } ?: ITALIAN
        }
    }
}
