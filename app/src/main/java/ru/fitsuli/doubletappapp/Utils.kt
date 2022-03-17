package ru.fitsuli.doubletappapp

import android.content.Context
import android.os.Parcelable
import android.util.TypedValue
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize


class Utils {
    companion object {

        @Parcelize
        enum class Priority(@StringRes val resId: Int) :
            Parcelable { HIGH(R.string.high), MEDIUM(R.string.medium), LOW(R.string.low) }

        @Parcelize
        enum class Type(@StringRes val resId: Int) :
            Parcelable { BAD(R.string.bad), NEUTRAL(R.string.neutral), GOOD(R.string.good) }

        fun Context.dpToPx(dpVal: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics)

        const val NEW_ITEM_KEY = "new_item"
        const val EDITED_ITEM_KEY = "edited_item"
        const val FRAGMENT_REQUEST_KEY = "item_from_habit"
    }
}