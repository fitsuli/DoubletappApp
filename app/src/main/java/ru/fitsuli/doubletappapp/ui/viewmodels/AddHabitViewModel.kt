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
import ru.fitsuli.doubletappapp.repository.HabitNetworkRepository

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _repo = HabitLocalRepository(application.applicationContext)
    private val _network = HabitNetworkRepository()
    private val _selectedItem: MutableLiveData<HabitItem?> = MutableLiveData()
    val selectedItem: LiveData<HabitItem?> = _selectedItem


    fun runFindItemById(id: String) {
        viewModelScope.launch(IO) {
            _repo.findById(id) {
                _selectedItem.postValue(it)
            }
        }
    }

    fun addWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.add(habit, onSuccess = { uid ->
                _repo.add(habit.copy(id = uid))
            })
        }
    }

    fun updateWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.update(habit, onSuccess = {
                _repo.update(habit)
            })
        }
    }

    fun deleteWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.delete(habit, onSuccess = {
                _repo.remove(habit)
            })
        }
    }

}