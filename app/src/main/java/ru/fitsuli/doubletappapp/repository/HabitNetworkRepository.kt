package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.Utils
import ru.fitsuli.doubletappapp.model.HabitItem

class HabitNetworkRepository {
    val habitApi = RetrofitRequestApi.getInstance()

    var habits = MutableLiveData(listOf<HabitItem>())

    suspend fun fetchAllHabits() = withContext(IO) {
        habits.postValue(habitApi.getHabits(Utils.AUTH_TOKEN))
    }
}