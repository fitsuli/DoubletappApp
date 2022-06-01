package ru.fitsuli.doubletappapp.data.storage.network

import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.data.storage.network.models.HabitUid

interface RemoteDataSource {
    suspend fun getAll(): List<HabitData>?

    suspend fun add(habit: HabitData): HabitUid?

    suspend fun addAll(items: List<HabitData>): Unit?

    suspend fun delete(habit: HabitData): Unit?

    suspend fun deleteAll(): Unit?

    suspend fun update(habit: HabitData): HabitUid?

    suspend fun markAsDone(habit: HabitData): Unit?
}