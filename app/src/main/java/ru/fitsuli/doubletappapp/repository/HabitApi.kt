package ru.fitsuli.doubletappapp.repository

import retrofit2.http.GET
import retrofit2.http.Header
import ru.fitsuli.doubletappapp.model.HabitItem

interface HabitApi {
    @GET("habit")
    suspend fun getHabits(@Header("Authorization") authorization: String): List<HabitItem>

}