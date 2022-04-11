package ru.fitsuli.doubletappapp.model

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fitsuli.doubletappapp.Priority
import ru.fitsuli.doubletappapp.Type

@Keep
@Entity(tableName = "habits")
data class HabitItem(
    val name: String, val description: String, val priority: Priority,
    val type: Type, val period: String, val count: String,
    @ColorInt val srgbColor: Int? = null, @PrimaryKey val id: Long
)
