package ru.fitsuli.doubletappapp.domain.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HabitUid(
    val uid: String
)
