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
        private var apiInstance: HabitApi? = null

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): HabitApi {
            if (apiInstance == null) {
                val contentType = "application/json".toMediaType()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpSingleton.getInstance())
                    .addCallAdapterFactory(NetworkResponseAdapterFactory())
                    .addConverterFactory(Json.asConverterFactory(contentType))
                    .build()
                apiInstance = retrofit.create(HabitApi::class.java)
            }
            return apiInstance!!
        }
    }
}