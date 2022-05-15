package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.LocalRepository
import ru.fitsuli.doubletappapp.repository.NetworkRepository

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = LocalRepository(application.applicationContext)
    private val _network = NetworkRepository()
    private val _selectedItem: MutableLiveData<HabitItem?> = MutableLiveData()
    val selectedItem: LiveData<HabitItem?> = _selectedItem


    fun runFindItemById(id: String) {
        viewModelScope.launch(IO) {
            _local.findById(id) {
                _selectedItem.postValue(it)
            }
        }
    }

    fun addWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.add(habit, onSuccess = { uid ->
                _local.add(habit.copy(id = uid))
            }, onError = {
                _local.add(habit.copy(isUploadPending = true))
            })
        }
    }

    fun updateWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.update(habit, onSuccess = {
                _local.update(habit)
            }, onError = {
                _local.update(habit.copy(isUpdatePending = true))
            })
        }
    }

    fun deleteWithRemote(habit: HabitItem) {
        viewModelScope.launch {
            _network.delete(habit, onSuccess = {
                _local.remove(habit)
            }, onError = {
                //
            })
        }
    }

}