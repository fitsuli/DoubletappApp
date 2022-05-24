package ru.fitsuli.doubletappapp.domain

import androidx.lifecycle.LiveData
import ru.fitsuli.doubletappapp.domain.models.HabitItem

interface HabitRepository {
    fun getHabits(): LiveData<List<HabitItem>>

    suspend fun getById(id: String): HabitItem?

    suspend fun add(habit: HabitItem)

    suspend fun delete(habit: HabitItem)

    suspend fun update(habit: HabitItem)

    suspend fun markAsDone(habit: HabitItem)

    suspend fun actualizePending()

    suspend fun actualizeDiff(): Unit?
}