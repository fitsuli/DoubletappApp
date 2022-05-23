package ru.fitsuli.doubletappapp.data.storage.network.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HabitDoneBody(
    @SerialName("date")
    val date: Int,

    @SerialName("habit_uid")
    val habitUid: String
)
