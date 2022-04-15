package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _repo = HabitLocalRepository(application.applicationContext)
    private val _selectedItem: MutableLiveData<HabitItem?> = MutableLiveData()
    val selectedItem: LiveData<HabitItem?> = _selectedItem

    fun add(item: HabitItem) {
        viewModelScope.launch(IO) {
            _repo.db.habitDao().insert(item)
        }
    }

    fun remove(item: HabitItem) {
        viewModelScope.launch(IO) {
            _repo.db.habitDao().delete(item)
        }
    }

    fun update(item: HabitItem) {
        viewModelScope.launch(IO) {
            _repo.db.habitDao().update(item)
        }
    }

    fun runFindItemById(id: Long) {
        viewModelScope.launch(IO) {
            _selectedItem.postValue(_repo.db.habitDao().findById(id))
        }
    }
}