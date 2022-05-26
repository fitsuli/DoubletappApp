package ru.fitsuli.doubletappapp.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.core.net.toUri
import ru.fitsuli.doubletappapp.R

@Keep
enum class FetchingErrorReason(@StringRes val hintStringResId: Int) {
    OFFLINE(R.string.offline_hint), REQUEST_ERROR(
        R.string.network_error
    )
}

class Utils {
    companion object {

        const val EDIT_MODE_KEY = "edit_mode"
        const val ITEM_ID_KEY = "item_id"
    }
}

fun Context.dpToPx(dpVal: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics)

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
fun Context.shortToast(@StringRes stringResId: Int) =
    Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()

fun String.toIntOrZero() = toIntOrNull() ?: 0

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