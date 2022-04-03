package ru.fitsuli.doubletappapp.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import ru.fitsuli.doubletappapp.Utils.Companion.Priority
import ru.fitsuli.doubletappapp.Utils.Companion.Type

@Keep
@Parcelize
data class HabitItem(
    val name: String, val description: String, val priority: Priority,
    val type: Type, val period: String, val count: String,
    @ColorInt val srgbColor: Int? = null, val id: Long
) : Parcelable
