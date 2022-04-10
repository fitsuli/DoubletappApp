package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _repo = HabitLocalRepository(application.applicationContext)

    private val _repoContent = _repo.listContent

    fun addItemToList(item: HabitItem) {
        _repo.db.habitDao().insert(item)
    }

    fun updateItemInList(item: HabitItem) {
        _repo.db.habitDao().update(item)
    }
}