package ru.fitsuli.doubletappapp.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HabitUid(
    val uid: String
)
