package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.Utils.Companion.SortBy
import ru.fitsuli.doubletappapp.model.HabitItem

object HabitLocalRepository {
    val listContent: MutableLiveData<MutableList<HabitItem>> = MutableLiveData(
        mutableListOf()
    )

    private fun List<HabitItem>.getFilteredList(searchStr: String) =
        if (searchStr.isNotBlank()) this.filter { item ->
            item.name.contains(
                searchStr,
                ignoreCase = true
            )
        }
        else this

    private fun getSortedList(sortBy: SortBy) = when (sortBy) {
        SortBy.ASCENDING -> listContent.value?.sortedBy { it.name }
        SortBy.DESCENDING -> listContent.value?.sortedByDescending { it.name }
        else -> listContent.value
    }

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            getSortedList(sortBy)?.getFilteredList(searchStr)
        }
}