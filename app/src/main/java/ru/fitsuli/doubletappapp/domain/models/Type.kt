package ru.fitsuli.doubletappapp.domain.models

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.R

@Keep
@Parcelize
@Serializable(with = TypeSerializer::class)
enum class Type(@StringRes val stringResId: Int, @IdRes val buttonResId: Int) :
    Parcelable { BAD(R.string.bad, R.id.radio_bad), GOOD(R.string.good, R.id.radio_good) }
