package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitItem

class MarkAsDoneUseCase(private val repository: HabitRepository) {
    suspend fun execute(habit: HabitItem) = repository.markAsDone(habit)
    suspend fun executeById(habitId: String): Unit? {
        return repository.getById(habitId)?.let {
            repository.markAsDone(it)
        }
    }
}