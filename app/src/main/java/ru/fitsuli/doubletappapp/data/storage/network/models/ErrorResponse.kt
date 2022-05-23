package ru.fitsuli.doubletappapp.data.storage.network.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ErrorResponse(
    val code: Int,
    val message: String
)
