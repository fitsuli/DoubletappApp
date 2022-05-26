package ru.fitsuli.doubletappapp.domain.models

import androidx.annotation.Keep

@Keep
enum class SortBy {
    ASCENDING, DESCENDING, NONE;

    companion object {
        fun SortBy?.orNone() = this ?: NONE
    }
}

