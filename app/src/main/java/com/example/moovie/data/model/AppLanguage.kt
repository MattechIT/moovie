package com.example.moovie.data.model

/**
 * Supported application languages.
 */
enum class AppLanguage(
    val code: String,
    val localeCode: String,
    val countryCode: String,
    val displayName: String
) {
    ITALIAN("it", "it-IT", "it", "Italiano"),
    ENGLISH("en", "en-US", "us", "English");

    /**
     * Compute country flag emoji dynamically using Regional Indicator Symbols.
     * E.g., "it" -> 🇮🇹, "us" -> 🇺🇸
     */
    val flagEmoji: String
        get() {
            if (countryCode.length != 2) return "🌐"
            val uppercaseCode = countryCode.uppercase()
            val firstChar = Character.codePointAt(uppercaseCode, 0) - 0x41 + 0x1F1E6
            val secondChar = Character.codePointAt(uppercaseCode, 1) - 0x41 + 0x1F1E6
            return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
        }

    companion object {
        fun fromString(value: String?): AppLanguage {
            return entries.firstOrNull { it.code == value || it.localeCode == value || it.name == value } ?: ITALIAN
        }
    }
}
