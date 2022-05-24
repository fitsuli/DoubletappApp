package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository

class GetHabitsUseCase(private val repository: HabitRepository) {
    fun execute() = repository.getHabits()
}