package ru.fitsuli.doubletappapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.parcelize.Parcelize


class Utils {
    companion object {

        @Keep
        @Parcelize
        enum class Priority(@StringRes val stringResId: Int) :
            Parcelable { HIGH(R.string.high), MEDIUM(R.string.medium), LOW(R.string.low) }

        @Keep
        @Parcelize
        enum class Type(@StringRes val stringResId: Int, @IdRes val buttonResId: Int) :
            Parcelable { BAD(R.string.bad, R.id.radio_bad), GOOD(R.string.good, R.id.radio_good) }

        fun Context.dpToPx(dpVal: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics)

        const val EDIT_MODE_KEY = "edit_mode"
        const val ITEM_ID_KEY = "item_id"
        const val HABIT_ITEM_KEY = "habit_data"
    }
}

fun Context.openLink(url: String) {
    try {
        startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                data = url.toUri()
            }
        )
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.link_open_error), Toast.LENGTH_SHORT).show()
    }
}

fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? =
    fragmentManager.findFragmentByTag("f$currentItem")

fun findVp2FragmentAtPosition(
    fragmentManager: FragmentManager,
    position: Int
): Fragment? = fragmentManager.findFragmentByTag("f$position")