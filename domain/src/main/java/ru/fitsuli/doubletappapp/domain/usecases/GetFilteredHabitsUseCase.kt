package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository

class GetFilteredHabitsUseCase(private val repository: HabitRepository) {
    fun execute() = repository.getFilteredHabits()
}