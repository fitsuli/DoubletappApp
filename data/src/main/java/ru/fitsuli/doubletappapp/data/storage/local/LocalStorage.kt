package ru.fitsuli.doubletappapp.data.storage.local

import android.content.Context
import ru.fitsuli.doubletappapp.data.models.HabitData
import ru.fitsuli.doubletappapp.domain.models.SearchSortFilter

class LocalStorage(context: Context) : LocalDataSource {
    private val db = AppDatabase.getInstance(context)

    override suspend fun getOnce() = db.habitDao().getOnce()

    override fun getFilteredSorted(filter: SearchSortFilter) =
        db.habitDao().getFilteredAndSorted(filter.filterStr, filter.sortBy)

    override suspend fun getById(id: String): HabitData? = db.habitDao().findById(id)

    override suspend fun add(habit: HabitData) = db.habitDao().insert(habit)

    override suspend fun addAll(items: List<HabitData>) =
        db.habitDao().insertAll(*items.toTypedArray())

    override suspend fun delete(habit: HabitData) = db.habitDao().delete(habit)

    override suspend fun deleteAll() = db.habitDao().deleteAll()

    override suspend fun update(habit: HabitData) = db.habitDao().update(habit)

}