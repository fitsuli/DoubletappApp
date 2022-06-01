package ru.fitsuli.doubletappapp.domain

import kotlinx.coroutines.flow.Flow
import ru.fitsuli.doubletappapp.domain.models.HabitDomain
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

interface HabitRepository {

    fun getFilteredHabits(): Flow<List<HabitDomain>>

    fun setFilterBy(filter: SearchSortFilter)

    suspend fun getById(id: String): HabitDomain?

    suspend fun add(habit: HabitDomain)

    suspend fun delete(habit: HabitDomain)

    suspend fun update(habit: HabitDomain)

    suspend fun markAsDone(habit: HabitDomain)

    suspend fun actualizePending(): Unit?
}