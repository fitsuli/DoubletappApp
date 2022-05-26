package ru.fitsuli.doubletappapp.domain.usecases

import ru.fitsuli.doubletappapp.domain.HabitRepository

class UpdateFromNetUseCase(private val repository: HabitRepository) {
    suspend fun execute(): Unit? {
        repository.actualizePending()
        return repository.actualizeDiff()
    }
}