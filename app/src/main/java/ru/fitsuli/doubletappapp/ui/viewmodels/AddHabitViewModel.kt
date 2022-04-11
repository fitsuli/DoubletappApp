package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _repo = HabitLocalRepository(application.applicationContext)

    fun addItemToList(item: HabitItem) {
        _repo.db.habitDao().insert(item)
    }

    fun removeItemFromList(item: HabitItem) {
        _repo.db.habitDao().delete(item)
    }

    fun updateItemInList(item: HabitItem) {
        _repo.db.habitDao().update(item)
    }

    fun findItemById(id: Long) = _repo.db.habitDao().findById(id)
}