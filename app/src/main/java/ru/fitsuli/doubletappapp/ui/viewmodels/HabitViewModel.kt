package ru.fitsuli.doubletappapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class HabitViewModel : ViewModel() {
    private val _repoContent: MutableLiveData<MutableList<HabitItem>> =
        HabitLocalRepository.listContent

    private val _searchStr: MutableLiveData<String> = MutableLiveData("")
    val searchStr: LiveData<String> = _searchStr

    val mediator = MediatorLiveData<List<HabitItem>>()

    init {
        mediator.addSource(searchStr) { s ->
            mediator.value = if (s.isNotBlank()) {
                _repoContent.value?.filter { item -> item.name.contains(s, ignoreCase = true) }
            } else {
                _repoContent.value
            }
        }

    }

    fun setFilterName(name: String) {
        _searchStr.value = name
    }

    fun getFilteredByTypeList(type: Type) = mediator.value?.filter { it.type == type }
    fun getFilteredByNameList(name: String) = mediator.value?.filter { it.name == name }
}