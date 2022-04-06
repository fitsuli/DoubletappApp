package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import ru.fitsuli.doubletappapp.Utils.Companion.SortBy
import ru.fitsuli.doubletappapp.model.HabitItem

object HabitLocalRepository {
    val listContent: MutableLiveData<MutableList<HabitItem>> = MutableLiveData(
        mutableListOf(/*
            HabitItem(
                "ddd",
                "fgh",
                Priority.MEDIUM,
                GOOD,
                "",
                "",
                id = UUID.randomUUID().mostSignificantBits
            ),
            HabitItem(
                "bbb",
                "fgh",
                Priority.MEDIUM,
                GOOD,
                "",
                "",
                id = UUID.randomUUID().mostSignificantBits
            ),
            HabitItem(
                "aaa",
                "fgh",
                Priority.MEDIUM,
                GOOD,
                "",
                "",
                id = UUID.randomUUID().mostSignificantBits
            ),
            HabitItem(
                "ccc",
                "fgh",
                Priority.MEDIUM,
                GOOD,
                "",
                "",
                id = UUID.randomUUID().mostSignificantBits
            ),
        */
        )
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

    fun getFilteredSortedList(searchStr: String, sortBy: SortBy) =
        getSortedList(sortBy)?.getFilteredList(searchStr)
}