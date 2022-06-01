package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.HabitDomain

class DeleteHabitUseCase(private val repository: HabitRepository) {
    suspend fun execute(habit: HabitDomain) = repository.delete(habit)
}