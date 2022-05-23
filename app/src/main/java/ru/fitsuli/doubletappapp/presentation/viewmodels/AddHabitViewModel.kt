package ru.fitsuli.doubletappapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.domain.models.HabitItem

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = LocalStorage(application.applicationContext)
    private val _network = NetworkStorage()
    private val _selectedItem: MutableLiveData<HabitItem?> = MutableLiveData()
    val selectedItem: LiveData<HabitItem?> = _selectedItem


    fun runFindItemById(id: String) {
        viewModelScope.launch(IO) {
            _local.getById(id)?.let {
                _selectedItem.postValue(it)
            }
        }
    }

    fun addWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.add(habit)?.let { uid ->
                _local.add(habit.copy(id = uid.uid))
            } ?: _local.add(habit.copy(isUploadPending = true))
        }
    }

    fun updateWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.update(habit)?.let {
                _local.update(habit)
            } ?: _local.update(habit.copy(isUpdatePending = true))
        }
    }

    fun deleteWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.delete(habit)?.let {
                _local.delete(habit)
            } // ?: { }
        }
    }

}