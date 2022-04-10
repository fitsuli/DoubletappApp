package ru.fitsuli.doubletappapp.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitLocalRepository(context: Context) {
    val db = AppDatabase.getInstance(context)

    val content = db.habitDao().getAll()

    private fun getFilteredList(searchStr: String) =
        if (searchStr.isNotBlank()) db.habitDao().filterByNameList(searchStr)
        else content.value.orEmpty()

    private fun List<HabitItem>.getSortedList(sortBy: SortBy) = when (sortBy) {
        SortBy.ASCENDING -> this.sortedBy { it.name }
        SortBy.DESCENDING -> this.sortedByDescending { it.name }
        else -> this
    }

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            // TODO: move to one big query
            getFilteredList(searchStr).getSortedList(sortBy)
        }
}