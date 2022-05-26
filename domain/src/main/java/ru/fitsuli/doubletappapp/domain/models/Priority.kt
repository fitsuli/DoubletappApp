package ru.fitsuli.doubletappapp.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.domain.R

@Keep
@Parcelize
@Serializable(with = PrioritySerializer::class)
enum class Priority(@StringRes val stringResId: Int) :
    Parcelable { HIGH(R.string.high), MEDIUM(R.string.medium), LOW(R.string.low) }
