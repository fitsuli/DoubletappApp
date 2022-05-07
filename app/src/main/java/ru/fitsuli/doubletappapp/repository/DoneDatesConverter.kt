package ru.fitsuli.doubletappapp.repository

import androidx.room.TypeConverter

class DoneDatesConverter {
    @TypeConverter
    fun storedStringToLanguages(value: String): List<Int> {
        val split = value.split("\\s*,\\s*")
        return buildList {
            split.forEach { add(it.toInt()) }
        }
    }

    @TypeConverter
    fun languagesToStoredString(cl: List<Int>) = buildString {
        cl.forEach { append("$it,") }
    }
}