package ru.fitsuli.doubletappapp.data.storage.network.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.data.DateTimeUTCSerializer
import java.time.OffsetDateTime

@Keep
@Serializable
data class HabitDoneBody(
    @SerialName("date")
    @Serializable(with = DateTimeUTCSerializer::class)
    val date: OffsetDateTime,

    @SerialName("habit_uid")
    val habitUid: String
)
