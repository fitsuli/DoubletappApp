package ru.fitsuli.doubletappapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class HabitViewModel : ViewModel() {
    private val _repoContent: MutableLiveData<MutableList<HabitItem>> =
        HabitLocalRepository.listContent
    val repoContent: LiveData<MutableList<HabitItem>> = HabitLocalRepository.listContent

    fun getFilteredList(type: Type) = repoContent.value?.filter { it.type == type }
}