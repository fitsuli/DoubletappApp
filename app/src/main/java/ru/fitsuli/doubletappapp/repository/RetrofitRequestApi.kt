package ru.fitsuli.doubletappapp.repository

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

class RetrofitRequestApi {
    companion object {
        private var apiInstance: HabitApi? = null

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): HabitApi {
            if (apiInstance == null) {
                val interceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                val okHttp = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

                val contentType = "application/json".toMediaType()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttp)
                    .addCallAdapterFactory(NetworkResponseAdapterFactory())
                    .addConverterFactory(Json.asConverterFactory(contentType))
                    .build()
                apiInstance = retrofit.create(HabitApi::class.java)
            }
            return apiInstance!!
        }
    }
}