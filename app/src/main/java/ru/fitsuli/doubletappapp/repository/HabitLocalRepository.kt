package ru.fitsuli.doubletappapp.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.SortBy

class HabitLocalRepository(context: Context) {
    val db = AppDatabase.getInstance(context)
    val content = db.habitDao().getAll()

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            db.habitDao().filterAndSort(searchStr, sortBy)
        }
}