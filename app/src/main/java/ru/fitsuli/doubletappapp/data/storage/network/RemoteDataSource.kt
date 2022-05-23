package ru.fitsuli.doubletappapp.data.storage.network

import ru.fitsuli.doubletappapp.data.storage.network.models.HabitUid
import ru.fitsuli.doubletappapp.domain.models.HabitItem

interface RemoteDataSource {
    suspend fun getAll(): List<HabitItem>?

    suspend fun add(habit: HabitItem): HabitUid?

    suspend fun addAll(items: List<HabitItem>): Unit?

    suspend fun delete(habit: HabitItem): Unit?

    suspend fun deleteAll(): Unit?

    suspend fun update(habit: HabitItem): HabitUid?
}