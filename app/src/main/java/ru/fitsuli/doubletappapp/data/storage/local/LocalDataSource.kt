package ru.fitsuli.doubletappapp.data.storage.local

import ru.fitsuli.doubletappapp.domain.models.HabitItem

interface LocalDataSource {
    suspend fun getAll(): List<HabitItem>

    suspend fun getById(id: String): HabitItem?

    suspend fun add(habit: HabitItem)

    suspend fun addAll(items: List<HabitItem>)

    suspend fun delete(habit: HabitItem)

    suspend fun deleteAll()

    suspend fun update(habit: HabitItem)


}