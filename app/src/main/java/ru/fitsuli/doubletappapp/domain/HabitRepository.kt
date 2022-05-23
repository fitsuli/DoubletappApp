package ru.fitsuli.doubletappapp.domain

import ru.fitsuli.doubletappapp.domain.models.HabitItem

interface HabitRepository {
    suspend fun getHabits(): List<HabitItem>

    suspend fun add(habit: HabitItem)

    suspend fun delete(habit: HabitItem)

    suspend fun update(habit: HabitItem)


}