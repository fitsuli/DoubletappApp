package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

class SaveFilterUseCase(private val repository: HabitRepository) {
    fun execute(filter: SearchSortFilter) = repository.setFilterBy(filter)
}