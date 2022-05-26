package ru.fitsuli.doubletappapp.data.storage.local

import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

interface LocalDataSource {
    fun getAll(): Flow<List<HabitItem>>

    fun getFilteredSorted(filter: SearchSortFilter): Flow<List<HabitItem>>

    suspend fun getOnce(): List<HabitItem>

    suspend fun getById(id: String): HabitItem?

    suspend fun add(habit: HabitItem)

    suspend fun addAll(items: List<HabitItem>)

    suspend fun delete(habit: HabitItem)

    suspend fun deleteAll()

    suspend fun update(habit: HabitItem)


}