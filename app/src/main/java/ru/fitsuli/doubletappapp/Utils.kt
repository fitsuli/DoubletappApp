package ru.fitsuli.doubletappapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Parcelable
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.Parcelize

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


class Utils {
    companion object {

        fun Context.dpToPx(dpVal: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics)

        const val EDIT_MODE_KEY = "edit_mode"
        const val ITEM_ID_KEY = "item_id"

        const val AUTH_TOKEN = "591d7ae2-6ed9-459d-a733-c3eb3e863796"
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

val Context.isOnline: Boolean
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }