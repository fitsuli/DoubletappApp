package ru.fitsuli.doubletappapp

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
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry

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

        const val AUTH_TOKEN = "591d7ae2-6ed9-459d-a733-c3eb3e863796"
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

suspend inline fun <T : Any, U : Any> executeWithConfiguredRetry(
    times: Int = 3,
    initialDelay: Long = 300, // ms
    maxDelay: Long = 1500,
    factor: Double = 2.0,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    block: suspend () -> NetworkResponse<T, U>
): NetworkResponse<T, U> = executeWithRetry(times, initialDelay, maxDelay, factor, block)

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