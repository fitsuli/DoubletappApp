package ru.fitsuli.doubletappapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.repository.HabitLocalRepository
import ru.fitsuli.doubletappapp.repository.HabitNetworkRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = HabitLocalRepository(application.applicationContext)
    private val _net = HabitNetworkRepository()

    private val _searchStr: MutableLiveData<String> = MutableLiveData("")
    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(SortBy.NONE)

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val mediator = MediatorLiveData<List<HabitItem>>()

    init {
        mediator.apply {
            addSource(_local.content) {
                viewModelScope.launch {
                    postValue(
                        _local.getFilteredSortedList(
                            _searchStr.value.orEmpty(),
                            _sortBy.value ?: SortBy.NONE
                        )
                    )
                }
            }
            addSource(_searchStr) { s ->
                viewModelScope.launch {
                    postValue(
                        _local.getFilteredSortedList(
                            s,
                            _sortBy.value ?: SortBy.NONE
                        )
                    )
                }
            }
            addSource(_sortBy) { sortBy ->
                viewModelScope.launch {
                    postValue(
                        _local.getFilteredSortedList(
                            _searchStr.value.orEmpty(),
                            sortBy
                        )
                    )
                }
            }
        }
    }

    fun updateHabitsFromNet() = viewModelScope.launch {
        _isLoading.postValue(true)

        _net.fetchAllHabits(onSuccess = {
            _local.removeAll()
            _local.addAll(it)
        })

        _isLoading.postValue(false)
    }

    fun getFilteredByTypeList(type: Type) = mediator.value?.filter { it.type == type }

    fun setSorting(sortBy: SortBy) {
        _sortBy.value = sortBy
    }

    fun setFilterName(name: String) {
        _searchStr.value = name
    }
}