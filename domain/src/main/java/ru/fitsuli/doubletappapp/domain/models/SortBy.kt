package ru.fitsuli.doubletappapp.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
enum class SortBy : Parcelable {
    ASCENDING, DESCENDING, NONE;

    companion object {
        fun SortBy?.orNone() = this ?: NONE
    }
}

