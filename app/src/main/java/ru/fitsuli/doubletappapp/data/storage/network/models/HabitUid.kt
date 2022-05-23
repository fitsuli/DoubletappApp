package ru.fitsuli.doubletappapp.data.storage.network.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HabitUid(
    val uid: String
)
