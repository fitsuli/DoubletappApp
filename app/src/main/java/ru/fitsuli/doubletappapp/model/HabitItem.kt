package ru.fitsuli.doubletappapp.model

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.Priority
import ru.fitsuli.doubletappapp.Type

@Keep
@Serializable
@Entity(tableName = "habits")
data class HabitItem(
    @SerialName("title") val name: String,
    val description: String,
    val priority: Priority,
    val type: Type,
    @SerialName("frequency") val period: String,
    val count: String,
    @SerialName("color") @ColorInt val srgbColor: Int? = null,
    @SerialName("uid") @PrimaryKey val id: Long
)
