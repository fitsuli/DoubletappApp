@file:UseSerializers(DateTimeUTCSerializer::class)

package ru.fitsuli.doubletappapp.data.models

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.UseSerializers
import ru.fitsuli.doubletappapp.domain.models.DateTimeUTCSerializer
import ru.fitsuli.doubletappapp.domain.models.Priority
import ru.fitsuli.doubletappapp.domain.models.Type
import java.time.OffsetDateTime

@Keep
@Serializable
@Entity(tableName = "habits")
data class HabitDTO(
    @SerialName("title") // TODO
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("priority")
    val priority: Priority,

    @SerialName("type")
    val type: Type,

    @SerialName("frequency")
    val period: Int,

    @SerialName("count")
    val count: Int,

    @SerialName("color")
    @ColorInt val srgbColor: Int? = null,

    @SerialName("date")
    val modifiedDate: OffsetDateTime,

    @SerialName("uid")
    @PrimaryKey val id: String,

    @SerialName("done_dates")
    val doneDates: List<OffsetDateTime> = emptyList(),

    @Transient
    val isUploadPending: Boolean = false,

    @Transient
    val isUpdatePending: Boolean = false
)