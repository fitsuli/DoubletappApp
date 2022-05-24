package ru.fitsuli.doubletappapp.data.storage.local

import androidx.lifecycle.LiveData
import ru.fitsuli.doubletappapp.domain.models.HabitItem

interface LocalDataSource {
    fun getAll(): LiveData<List<HabitItem>>

    suspend fun getById(id: String): HabitItem?

    suspend fun add(habit: HabitItem)

    suspend fun addAll(items: List<HabitItem>)

    suspend fun delete(habit: HabitItem)

    suspend fun deleteAll()

    suspend fun update(habit: HabitItem)


}