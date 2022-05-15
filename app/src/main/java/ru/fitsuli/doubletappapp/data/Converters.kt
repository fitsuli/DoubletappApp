package ru.fitsuli.doubletappapp.data

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun storedStringToDates(value: String): List<Int> {
        if (value.isEmpty()) return emptyList()

        val split = value.split("\\s*,\\s*")
        return buildList {
            split.forEach { add(it.toInt()) }
        }
    }

    @TypeConverter
    fun datesToStoredString(cl: List<Int>) = buildString {
        cl.forEach { append("$it,") }
    }


    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}