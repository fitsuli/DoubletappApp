package ru.fitsuli.doubletappapp.presentation.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter
import ru.fitsuli.doubletappapp.domain.models.SortBy
import ru.fitsuli.doubletappapp.domain.models.Type
import ru.fitsuli.doubletappapp.domain.usecases.GetFilteredHabitsUseCase
import ru.fitsuli.doubletappapp.domain.usecases.MarkAsDoneUseCase
import ru.fitsuli.doubletappapp.domain.usecases.SaveFilterUseCase
import ru.fitsuli.doubletappapp.domain.usecases.UpdateFromNetUseCase
import ru.fitsuli.doubletappapp.presentation.FetchingErrorReason
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val _markDoneHabit: MarkAsDoneUseCase,
    private val _updateNetHabits: UpdateFromNetUseCase,
    private val _getFilteredHabits: GetFilteredHabitsUseCase,
    private val _saveFilterSettings: SaveFilterUseCase,
) : ViewModel() {

    private var filter = SearchSortFilter()
    val filtered = _getFilteredHabits.execute().asLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateHabitsFromNet(
        onFetchingError: suspend (FetchingErrorReason) -> Unit = {}
    ) = viewModelScope.launch {
        _isLoading.postValue(true)

        _updateNetHabits.execute()
            ?: withContext(Main) { onFetchingError(FetchingErrorReason.REQUEST_ERROR) }

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