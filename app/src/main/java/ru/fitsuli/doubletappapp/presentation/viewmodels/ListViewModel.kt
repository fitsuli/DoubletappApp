package ru.fitsuli.doubletappapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.FetchingErrorReason
import ru.fitsuli.doubletappapp.data.repository.HabitRepositoryImpl
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.data.storage.network.RemoteDataSource
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter
import ru.fitsuli.doubletappapp.domain.models.SortBy
import ru.fitsuli.doubletappapp.domain.models.SortBy.Companion.orNone
import ru.fitsuli.doubletappapp.domain.models.Type
import ru.fitsuli.doubletappapp.domain.usecases.GetFilteredHabitsUseCase
import ru.fitsuli.doubletappapp.domain.usecases.GetHabitsUseCase
import ru.fitsuli.doubletappapp.domain.usecases.MarkAsDoneUseCase
import ru.fitsuli.doubletappapp.domain.usecases.UpdateFromNetUseCase
import ru.fitsuli.doubletappapp.isOnline

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val _local = LocalStorage(application.applicationContext)
    private val _net: RemoteDataSource = NetworkStorage()
    private val _repoImpl = HabitRepositoryImpl(_local, _net)

    private val _getAllHabits = GetHabitsUseCase(_repoImpl)
    private val _markDoneHabit = MarkAsDoneUseCase(_repoImpl)
    private val _updateNetHabits = UpdateFromNetUseCase(_repoImpl)
    private val _getFilteredHabits = GetFilteredHabitsUseCase(_repoImpl)

    private val _searchStr: MutableLiveData<String> = MutableLiveData("")
    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(SortBy.NONE)

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val mediator = MediatorLiveData<List<HabitItem>>()

    init {
        mediator.apply {
            addSource(_getAllHabits.execute().asLiveData()) {
                viewModelScope.launch {
                    _getFilteredHabits.execute(
                        SearchSortFilter(
                            filterStr = _searchStr.value.orEmpty(),
                            sortBy = _sortBy.value.orNone()
                        )
                    )
                }
            }
            addSource(_searchStr) { s ->
                viewModelScope.launch {
                    postValue(
                        _getFilteredHabits.execute(
                            SearchSortFilter(
                                filterStr = s,
                                sortBy = _sortBy.value.orNone()
                            )
                        )
                    )
                }
            }
            addSource(_sortBy) { sortBy ->
                viewModelScope.launch {
                    postValue(
                        _getFilteredHabits.execute(
                            SearchSortFilter(
                                filterStr = _searchStr.value.orEmpty(),
                                sortBy = sortBy
                            )
                        )
                    )
                }
            }
        }
/*
        viewModelScope.launch {
            _getAllHabits.execute().collect {
                mediator.postValue(

                    _local.getFilteredSorted(
                        _searchStr.value.orEmpty(),
                        _sortBy.value ?: SortBy.NONE
                    )
                )
            }
        }
*/
    }

    fun updateHabitsFromNet(
        onFetchingError: suspend (FetchingErrorReason) -> Unit = {}
    ) = viewModelScope.launch {
        _isLoading.postValue(true)

        if (getApplication<Application>().applicationContext.isOnline) {
            _updateNetHabits.execute()
                ?: withContext(Main) { onFetchingError(FetchingErrorReason.REQUEST_ERROR) }
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