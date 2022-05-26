package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository

class FindHabitByIdUseCase(private val repository: HabitRepository) {
    suspend fun execute(id: String) = repository.getById(id)
}