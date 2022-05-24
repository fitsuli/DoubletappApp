package ru.fitsuli.doubletappapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.data.repository.HabitRepositoryImpl
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.usecases.AddHabitUseCase
import ru.fitsuli.doubletappapp.domain.usecases.DeleteHabitUseCase
import ru.fitsuli.doubletappapp.domain.usecases.FindHabitByIdUseCase
import ru.fitsuli.doubletappapp.domain.usecases.UpdateHabitUseCase

class AddHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = LocalStorage(application.applicationContext)
    private val _network = NetworkStorage()
    private val _repoImpl = HabitRepositoryImpl(_local, _network)

    private val _addHabit = AddHabitUseCase(_repoImpl)
    private val _deleteHabit = DeleteHabitUseCase(_repoImpl)
    private val _updateHabit = UpdateHabitUseCase(_repoImpl)
    private val _findHabit = FindHabitByIdUseCase(_repoImpl)

    private val _selectedItem: MutableLiveData<HabitItem?> = MutableLiveData()
    val selectedItem: LiveData<HabitItem?> = _selectedItem


    fun runFindItemById(id: String) {
        viewModelScope.launch(IO) {
            _findHabit.execute(id)?.let {
                _selectedItem.postValue(it)
            }
        }
    }

    fun add(habit: HabitItem) {
        viewModelScope.launch {
            _addHabit.execute(habit)
        }
    }

    fun update(habit: HabitItem) {
        viewModelScope.launch {
            _updateHabit.execute(habit)
        }
    }

    fun delete(habit: HabitItem) {
        viewModelScope.launch {
            _deleteHabit.execute(habit)
        }
    }

}