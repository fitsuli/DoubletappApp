package ru.fitsuli.doubletappapp.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitLocalRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    val content = db.habitDao().getAll()

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            db.habitDao().filterAndSort(searchStr, sortBy)
        }

    suspend fun findById(id: String, onFound: (item: HabitItem) -> Unit) = withContext(IO) {
        db.habitDao().findById(id)?.let {
            onFound(it)
        }
    }

    suspend fun addLocal(item: HabitItem) = withContext(IO) {
        db.habitDao().insert(item)
    }

    suspend fun addAllLocal(items: List<HabitItem>) = withContext(IO) {
        db.habitDao().insertAll(*items.toTypedArray())
    }

    suspend fun removeLocal(item: HabitItem) = withContext(IO) {
        db.habitDao().delete(item)
    }

    suspend fun updateLocal(item: HabitItem) = withContext(IO) {
        db.habitDao().update(item)
    }
}