package ru.fitsuli.doubletappapp.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitLocalRepository(context: Context) {
    val db = AppDatabase.getInstance(context)

    val listContent = db.habitDao().getAll()

    private fun List<HabitItem>.getFilteredList(searchStr: String) =
        if (searchStr.isNotBlank()) this.filter { item ->
            item.name.contains(
                searchStr,
                ignoreCase = true
            )
        }
        else this

    private fun List<HabitItem>.getSortedList(sortBy: SortBy) = when (sortBy) {
        SortBy.ASCENDING -> this.sortedBy { it.name }
        SortBy.DESCENDING -> this.sortedByDescending { it.name }
        else -> this
    }

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            listContent.value?.getSortedList(sortBy)?.getFilteredList(searchStr)
        }
}