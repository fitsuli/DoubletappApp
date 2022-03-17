package ru.fitsuli.doubletappapp

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize
import ru.fitsuli.doubletappapp.Utils.Companion.Priority
import ru.fitsuli.doubletappapp.Utils.Companion.Type

@Parcelize
data class HabitItem(
    val name: String, val description: String, val priorityPosition: Priority,
    val type: Type, val period: String, val count: String,
    @ColorInt val srgbColor: Int? = null, val id: Int
) : Parcelable
