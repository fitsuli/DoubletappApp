package ru.fitsuli.doubletappapp.domain.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
)
