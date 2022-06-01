package ru.fitsuli.doubletappapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.domain.models.HabitDomain
import ru.fitsuli.doubletappapp.domain.usecases.AddHabitUseCase
import ru.fitsuli.doubletappapp.domain.usecases.DeleteHabitUseCase
import ru.fitsuli.doubletappapp.domain.usecases.FindHabitByIdUseCase
import ru.fitsuli.doubletappapp.domain.usecases.UpdateHabitUseCase
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val _addHabit: AddHabitUseCase,
    private val _deleteHabit: DeleteHabitUseCase,
    private val _updateHabit: UpdateHabitUseCase,
    private val _findHabit: FindHabitByIdUseCase
) : ViewModel() {

    private val _selectedItem: MutableLiveData<HabitDomain?> = MutableLiveData()
    val selectedItem: LiveData<HabitDomain?> = _selectedItem


    fun runFindItemById(id: String) {
        viewModelScope.launch(IO) {
            _findHabit.execute(id)?.let {
                _selectedItem.postValue(it)
            }
        }
    }

    fun add(habit: HabitDomain) {
        viewModelScope.launch {
            _addHabit.execute(habit)
        }
    }

    fun update(habit: HabitDomain) {
        viewModelScope.launch {
            _updateHabit.execute(habit)
        }
    }

    fun delete(habit: HabitDomain) {
        viewModelScope.launch {
            _deleteHabit.execute(habit)
        }
    }

}