package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository
import ru.fitsuli.doubletappapp.repository.HabitNetworkRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val _repoLocal = HabitLocalRepository(application.applicationContext)

    val _api = HabitNetworkRepository().habitApi

    private val _searchStr: MutableLiveData<String> = MutableLiveData("")
    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(SortBy.NONE)

    val mediator = MediatorLiveData<List<HabitItem>>()

    init {
        mediator.apply {
            addSource(_repoLocal.content) {
                viewModelScope.launch {
                    postValue(
                        _repoLocal.getFilteredSortedList(
                            _searchStr.value.orEmpty(),
                            _sortBy.value ?: SortBy.NONE
                        )
                    )
                }
            }
            addSource(_searchStr) { s ->
                viewModelScope.launch {
                    postValue(
                        _repoLocal.getFilteredSortedList(
                            s,
                            _sortBy.value ?: SortBy.NONE
                        )
                    )
                }
            }
            addSource(_sortBy) { sortBy ->
                viewModelScope.launch {
                    postValue(
                        _repoLocal.getFilteredSortedList(
                            _searchStr.value.orEmpty(),
                            sortBy
                        )
                    )
                }
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