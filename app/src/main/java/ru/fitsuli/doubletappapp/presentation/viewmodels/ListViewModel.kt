package ru.fitsuli.doubletappapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.FetchingErrorReason
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.Type
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.isOnline

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = LocalStorage(application.applicationContext)
    private val _net = NetworkStorage()

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

    fun updateHabitsFromNet(
        onFetchingError: suspend (FetchingErrorReason) -> Unit = {},
        onHabitUploaded: suspend (HabitItem) -> Unit = {},
        onHabitUpdated: suspend (HabitItem) -> Unit = {}
    ) = viewModelScope.launch {
        _isLoading.postValue(true)

        _local.content.value?.forEach { habit ->
            if (habit.isUploadPending) {
                _net.add(habit)?.let { uid ->
                    _local.delete(habit)
                    _local.add(habit.copy(id = uid.uid, isUpdatePending = false))
                    withContext(Main) { onHabitUploaded(habit) }
                }

                return@forEach
            }

            if (habit.isUpdatePending) {
                _net.update(habit)?.let {
                    withContext(Main) { onHabitUpdated(habit) }
                }

                return@forEach
            }
        }

        if (getApplication<Application>().applicationContext.isOnline) {
            _net.getAll()?.let {
                _local.deleteAll()
                _local.addAll(it)
            } ?: withContext(Main) { onFetchingError(FetchingErrorReason.REQUEST_ERROR) }
        } else {
            withContext(Main) { onFetchingError(FetchingErrorReason.OFFLINE) }
        }

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