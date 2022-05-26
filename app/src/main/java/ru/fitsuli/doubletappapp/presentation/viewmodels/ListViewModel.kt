package ru.fitsuli.doubletappapp.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.data.repository.HabitRepositoryImpl
import ru.fitsuli.doubletappapp.data.storage.local.LocalDataSource
import ru.fitsuli.doubletappapp.data.storage.local.LocalStorage
import ru.fitsuli.doubletappapp.data.storage.network.NetworkStorage
import ru.fitsuli.doubletappapp.data.storage.network.RemoteDataSource
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter
import ru.fitsuli.doubletappapp.domain.models.SortBy
import ru.fitsuli.doubletappapp.domain.models.Type
import ru.fitsuli.doubletappapp.domain.usecases.GetFilteredHabitsUseCase
import ru.fitsuli.doubletappapp.domain.usecases.MarkAsDoneUseCase
import ru.fitsuli.doubletappapp.domain.usecases.SaveFilterUseCase
import ru.fitsuli.doubletappapp.domain.usecases.UpdateFromNetUseCase
import ru.fitsuli.doubletappapp.presentation.FetchingErrorReason
import ru.fitsuli.doubletappapp.presentation.isOnline

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val _local: LocalDataSource = LocalStorage(application.applicationContext)
    private val _net: RemoteDataSource = NetworkStorage()
    private val _repoImpl = HabitRepositoryImpl(_local, _net)

    private val _markDoneHabit = MarkAsDoneUseCase(_repoImpl)
    private val _updateNetHabits = UpdateFromNetUseCase(_repoImpl)
    private val _getFilteredHabits = GetFilteredHabitsUseCase(_repoImpl)
    private val _saveFilterSettings = SaveFilterUseCase(_repoImpl)

    private var filter = SearchSortFilter()
    val filtered = _getFilteredHabits.execute(filter).asLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

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

    fun getFilteredByTypeList(type: Type) = filtered.value?.filter { it.type == type }

    fun setSorting(sortBy: SortBy) {
        filter.copy(sortBy = sortBy).let {
            _saveFilterSettings.execute(it)
            filter = it
        }
    }

    fun setFilterName(name: String) {
        filter.copy(filterStr = name).let {
            _saveFilterSettings.execute(it)
            filter = it
        }
    }

    fun markHabitAsDone(habitId: String) {
        viewModelScope.launch {
            _markDoneHabit.executeById(habitId)
        }
    }
}