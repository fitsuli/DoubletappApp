package ru.fitsuli.doubletappapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.fitsuli.doubletappapp.Utils.Companion.SortBy
import ru.fitsuli.doubletappapp.Utils.Companion.Type
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository

class HabitViewModel : ViewModel() {
    private val _repoContent: MutableLiveData<MutableList<HabitItem>> =
        HabitLocalRepository.listContent

    private val _searchStr: MutableLiveData<String> = MutableLiveData("")
    val searchStr: LiveData<String> = _searchStr

    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(SortBy.NONE)

    val mediator = MediatorLiveData<List<HabitItem>>()

    init {
        mediator.apply {
            addSource(_repoContent) {
                value =
                    HabitLocalRepository.getFilteredSortedList(_searchStr.value!!, _sortBy.value!!)
            }
            addSource(_searchStr) { s ->
                value = HabitLocalRepository.getFilteredSortedList(s, _sortBy.value!!)
            }
            addSource(_sortBy) { sortBy ->
                value = HabitLocalRepository.getFilteredSortedList(_searchStr.value!!, sortBy)
            }
        }
    }

    fun getFilteredByTypeList(type: Type) = mediator.value?.filter { it.type == type }

    fun setSorting(sortBy: SortBy) {
        _sortBy.value = sortBy
    }

    fun setFilterName(name: String) {
        _searchStr.value = name
    }
}