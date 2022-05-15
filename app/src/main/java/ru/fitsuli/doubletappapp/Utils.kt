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
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.fitsuli.doubletappapp.repository.PrioritySerializer
import ru.fitsuli.doubletappapp.repository.TypeSerializer

@Keep
@Parcelize
@Serializable(with = PrioritySerializer::class)
enum class Priority(@StringRes val stringResId: Int) :
    Parcelable { HIGH(R.string.high), MEDIUM(R.string.medium), LOW(R.string.low) }

@Keep
@Parcelize
@Serializable(with = TypeSerializer::class)
enum class Type(@StringRes val stringResId: Int, @IdRes val buttonResId: Int) :
    Parcelable { BAD(R.string.bad, R.id.radio_bad), GOOD(R.string.good, R.id.radio_good) }

@Keep
@Parcelize
enum class SortBy : Parcelable { ASCENDING, DESCENDING, NONE }

@Keep
enum class FetchingErrorReason(@StringRes val hintStringResId: Int) {
    OFFLINE(R.string.offline_hint), REQUEST_ERROR(
        R.string.network_error
    )
}

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