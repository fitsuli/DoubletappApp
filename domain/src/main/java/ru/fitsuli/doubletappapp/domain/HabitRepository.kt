package ru.fitsuli.doubletappapp.domain

import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

interface HabitRepository {
    fun getHabits(): Flow<List<HabitItem>>

    fun getFilteredHabits(): Flow<List<HabitItem>>

    fun setFilterBy(filter: SearchSortFilter)

    suspend fun getById(id: String): HabitItem?

    suspend fun add(habit: HabitItem)

    suspend fun delete(habit: HabitItem)

    suspend fun update(habit: HabitItem)

    suspend fun markAsDone(habit: HabitItem)

    suspend fun actualizePending(): Unit?
}