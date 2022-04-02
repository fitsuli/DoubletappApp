package ru.fitsuli.doubletappapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fitsuli.doubletappapp.Utils.Companion.Type

class HabitViewModel : ViewModel() {
    private val _listContent: MutableLiveData<MutableList<HabitItem>> = MutableLiveData(
        mutableListOf()
    )
    val listContent: LiveData<MutableList<HabitItem>> = _listContent

    init {}

    fun getFilteredList(type: Type) = listContent.value?.filter { it.type == type }

    fun addItemToList(item: HabitItem) {
        _listContent += item
    }

    fun updateItemInList(item: HabitItem) {
        _listContent.value?.let { list ->
            _listContent[list.indexOfFirst { it.id == item.id }] = item
        }
    }
}