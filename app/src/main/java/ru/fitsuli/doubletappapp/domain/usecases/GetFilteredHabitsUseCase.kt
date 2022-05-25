package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

class GetFilteredHabitsUseCase(private val repository: HabitRepository) {
    suspend fun execute(filter: SearchSortFilter) = repository.getFilteredHabits(filter)
}