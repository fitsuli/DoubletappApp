package ru.fitsuli.doubletappapp.domain.models

import androidx.annotation.ColorInt
import java.time.OffsetDateTime

data class HabitDomain(
    val name: String,

    val description: String,

    val priority: Priority,

    val type: Type,

    val period: Int,

    val count: Int,

    @ColorInt val srgbColor: Int? = null,

    val modifiedDate: OffsetDateTime,

    val id: String,

    val doneDates: List<OffsetDateTime> = emptyList(),

    @Transient
    val isUploadPending: Boolean = false,

    @Transient
    val isUpdatePending: Boolean = false
)
