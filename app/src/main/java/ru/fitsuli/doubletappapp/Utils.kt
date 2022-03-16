package ru.fitsuli.doubletappapp

import android.content.Context
import android.graphics.Color
import android.util.TypedValue


class Utils {
    companion object {
        object HabitType {
            const val Bad = 0
            const val Neutral = 1
            const val Good = 2
        }

        object Priority {
            const val High = 0
            const val Medium = 1
            const val Low = 2
        }

        fun Context.dpToPx(dpVal: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics)

        val HUE_COLORS = intArrayOf(
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.RED
        )
    }
}