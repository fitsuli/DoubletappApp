package ru.fitsuli.doubletappapp.domain.models

import androidx.annotation.Keep
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.domain.R

@Keep
@Serializable(with = PrioritySerializer::class)
enum class Priority(@StringRes val stringResId: Int) {
    HIGH(R.string.high), MEDIUM(R.string.medium), LOW(
        R.string.low
    )
}
