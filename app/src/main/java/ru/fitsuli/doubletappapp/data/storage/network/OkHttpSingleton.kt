package ru.fitsuli.doubletappapp.data.storage.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.fitsuli.doubletappapp.BuildConfig

class OkHttpSingleton {
    companion object {
        private var apiInstance: OkHttpClient? = null

        private fun getOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            }

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        fun getInstance(): OkHttpClient {
            if (apiInstance == null) {
                apiInstance = getOkHttpClient()
            }
            return apiInstance!!
        }
    }
}