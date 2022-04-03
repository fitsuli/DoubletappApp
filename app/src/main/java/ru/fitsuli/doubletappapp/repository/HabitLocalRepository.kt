package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import ru.fitsuli.doubletappapp.HabitItem

object HabitLocalRepository {
    val listContent: MutableLiveData<MutableList<HabitItem>> = MutableLiveData(
        mutableListOf()
    )
}