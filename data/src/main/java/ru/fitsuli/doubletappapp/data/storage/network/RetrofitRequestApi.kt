package ru.fitsuli.doubletappapp.data.storage.network

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

class RetrofitRequestApi {

    companion object {

        @OptIn(ExperimentalSerializationApi::class)
        private val retrofit: Retrofit by lazy {

            val contentType = "application/json".toMediaType()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpSingleton.instance)
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
        }

        val instance: HabitApi by lazy {
            retrofit.create(HabitApi::class.java)
        }
    }
}