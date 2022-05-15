package ru.fitsuli.doubletappapp.data

import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*
import ru.fitsuli.doubletappapp.domain.models.ErrorResponse
import ru.fitsuli.doubletappapp.domain.models.HabitDoneBody
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.HabitUid

interface HabitApi {
    @GET("habit")
    suspend fun getAll(@Header("Authorization") authorization: String): NetworkResponse<List<HabitItem>, ErrorResponse>

    @PUT("habit")
    suspend fun add(
        @Header("Authorization") authorization: String,
        @Body habitItem: HabitItem
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