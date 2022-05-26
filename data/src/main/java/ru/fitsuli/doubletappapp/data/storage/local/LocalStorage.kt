package ru.fitsuli.doubletappapp.data.storage.local

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

class LocalStorage(context: Context) : LocalDataSource {
    private val db = AppDatabase.getInstance(context)

    override fun getAll() = db.habitDao().getAll()

    override suspend fun getOnce() = db.habitDao().getOnce()

    override fun getFilteredSorted(filter: SearchSortFilter) =
        db.habitDao().getFilteredAndSorted(filter.filterStr, filter.sortBy)

    override suspend fun getById(id: String): HabitItem? = withContext(IO) {
        db.habitDao().findById(id)
    }

    override suspend fun add(habit: HabitItem) = withContext(IO) {
        db.habitDao().insert(habit)
    }

    override suspend fun addAll(items: List<HabitItem>) = withContext(IO) {
        db.habitDao().insertAll(*items.toTypedArray())
    }

    override suspend fun delete(habit: HabitItem) = withContext(IO) {
        db.habitDao().delete(habit)
    }

    override suspend fun deleteAll() = withContext(IO) {
        db.habitDao().deleteAll()
    }

    override suspend fun update(habit: HabitItem) = withContext(IO) {
        db.habitDao().update(habit)
    }
}