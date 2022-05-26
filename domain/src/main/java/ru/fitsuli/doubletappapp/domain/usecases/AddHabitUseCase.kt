package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitItem

class AddHabitUseCase(private val repository: HabitRepository) {
    suspend fun execute(habit: HabitItem) = repository.add(habit)
}