package ru.fitsuli.doubletappapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.plusAssign
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository
import ru.fitsuli.doubletappapp.set

class AddHabitViewModel : ViewModel() {
    private val _repoContent: MutableLiveData<MutableList<HabitItem>> =
        HabitLocalRepository.listContent
    val repoContent: LiveData<MutableList<HabitItem>> = HabitLocalRepository.listContent


    fun addItemToList(item: HabitItem) {
        _repoContent += item
    }

    fun updateItemInList(item: HabitItem) {
        _repoContent.value?.let { list ->
            _repoContent[list.indexOfFirst { it.id == item.id }] = item
        }
    }
}