package ru.fitsuli.doubletappapp.data.storage.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry

const val AUTH_TOKEN = "591d7ae2-6ed9-459d-a733-c3eb3e863796"

suspend inline fun <T : Any, U : Any> executeWithConfiguredRetry(
    times: Int = 3,
    initialDelay: Long = 300, // ms
    maxDelay: Long = 1500,
    factor: Double = 2.0,
    @Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
    block: suspend () -> NetworkResponse<T, U>
): NetworkResponse<T, U> = executeWithRetry(times, initialDelay, maxDelay, factor, block)
