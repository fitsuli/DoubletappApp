package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import ru.fitsuli.doubletappapp.model.HabitItem

object HabitLocalRepository {
    val listContent: MutableLiveData<MutableList<HabitItem>> = MutableLiveData(
        mutableListOf()
    )
}