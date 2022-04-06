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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

        @Keep
        @Parcelize
        enum class SortBy : Parcelable { ASCENDING, DESCENDING, NONE }

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
        shortToast(getString(R.string.link_open_error))
    }
}

fun Context.shortToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.set(index: Int, item: T) {
    val value = this.value ?: mutableListOf()
    value[index] = item
    this.value = value
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}