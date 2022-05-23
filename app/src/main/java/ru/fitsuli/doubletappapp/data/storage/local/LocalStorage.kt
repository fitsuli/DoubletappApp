package ru.fitsuli.doubletappapp.data.storage.local

import android.content.Context
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.SortBy
import ru.fitsuli.doubletappapp.domain.models.HabitItem

class LocalStorage(context: Context) : LocalDataSource {
    private val db = AppDatabase.getInstance(context)
    val content = db.habitDao().getAll()

    override suspend fun getAll() = content.value.orEmpty()

    suspend fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        withContext(IO) {
            db.habitDao().filterAndSort(searchStr, sortBy)
        }

    override suspend fun getById(id: String): HabitItem? {
        return db.habitDao().findById(id)
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