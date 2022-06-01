package ru.fitsuli.doubletappapp.data.storage.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.fitsuli.doubletappapp.data.BuildConfig

class OkHttpSingleton {
    companion object {
        private val interceptor by lazy {
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            }
        }
        val instance: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }
    }
}