package ru.fitsuli.doubletappapp.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.fitsuli.doubletappapp.Priority
import ru.fitsuli.doubletappapp.Type

@Keep
@Parcelize
@Entity(tableName = "habits")
data class HabitItem(
    val name: String, val description: String, val priority: Priority,
    val type: Type, val period: String, val count: String,
    @ColorInt val srgbColor: Int? = null, @PrimaryKey val id: Long
) : Parcelable
