package ru.fitsuli.doubletappapp.data.repository

import ru.fitsuli.doubletappapp.data.storage.local.LocalDataSource
import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitItem

class HabitRepositoryImpl(private val localStorage: LocalDataSource) : HabitRepository {
    override suspend fun getHabits(): List<HabitItem> {
        return localStorage.getAll()
    }

    override suspend fun add(habit: HabitItem) {
        return localStorage.add(habit)
    }

    override suspend fun delete(habit: HabitItem) {
        return localStorage.delete(habit)
    }

    override suspend fun update(habit: HabitItem) {
        return localStorage.update(habit)
    }


}