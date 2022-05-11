package ru.fitsuli.doubletappapp.model

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
