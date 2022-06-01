package ru.fitsuli.doubletappapp.data.storage.network

import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.data.storage.network.models.ErrorResponse
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitDoneBody
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitUid

interface HabitApi {
    @GET("habit")
    suspend fun getAll(@Header("Authorization") authorization: String): NetworkResponse<List<HabitData>, ErrorResponse>

    @PUT("habit")
    suspend fun add(
        @Header("Authorization") authorization: String,
        @Body habitData: HabitData
    ): NetworkResponse<HabitUid, ErrorResponse>

    // DELETE with body is ambiguous
    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun delete(
        @Header("Authorization") authorization: String,
        @Body uidHabit: HabitUid
    ): NetworkResponse<Unit, ErrorResponse>

    @POST("habit_done")
    suspend fun markAsDone(
        @Header("Authorization") authorization: String,
        @Body habitDone: HabitDoneBody
    ): NetworkResponse<Unit, ErrorResponse>
}