package ru.fitsuli.doubletappapp.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.domain.R

@Keep
@Parcelize
@Serializable(with = TypeSerializer::class)
enum class Type(@StringRes val stringResId: Int) :
    Parcelable { BAD(R.string.bad), GOOD(R.string.good) }
