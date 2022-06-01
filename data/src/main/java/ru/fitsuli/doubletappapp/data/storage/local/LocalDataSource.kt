package ru.fitsuli.doubletappapp.data.storage.local

import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

interface LocalDataSource {

    fun getFilteredSorted(filter: SearchSortFilter): Flow<List<HabitData>>

    suspend fun getOnce(): List<HabitData>

    suspend fun getById(id: String): HabitData?

    suspend fun add(habit: HabitData)

    suspend fun addAll(items: List<HabitData>)

    suspend fun delete(habit: HabitData)

    suspend fun deleteAll()

    suspend fun update(habit: HabitData)


}