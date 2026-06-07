package com.example.moovie.data.model

/**
 * Enumeration of available application themes.
 */
enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        /**
         * Resolves the theme from its string representation, defaulting to SYSTEM if invalid.
         */
        fun fromString(value: String?): AppTheme {
            return entries.firstOrNull { it.name == value } ?: SYSTEM
        }
    }
}
