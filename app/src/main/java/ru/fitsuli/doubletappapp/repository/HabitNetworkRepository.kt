package ru.fitsuli.doubletappapp.repository

import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.invoke
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.Utils.Companion.AUTH_TOKEN
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.model.HabitUid

class HabitNetworkRepository {
    private val habitApi = RetrofitRequestApi.getInstance()
    var habits = MutableLiveData(listOf<HabitItem>())

    // TODO: onError callbacks

    suspend fun fetchAllHabits(onSuccess: suspend (List<HabitItem>) -> Unit = {}) =
        withContext(IO) {
            habitApi.getAll(AUTH_TOKEN).invoke()?.let {
                habits.postValue(it)
                onSuccess(it)
            }
        }

    suspend fun add(habit: HabitItem, onSuccess: suspend (uid: String) -> Unit = {}) =
        withContext(IO) {
            habitApi.add(
                AUTH_TOKEN, habit.copy(id = "") // required by API
            ).invoke()?.let {
                fetchAllHabits()
                onSuccess(it.uid)
            }
        }

    suspend fun update(habit: HabitItem, onSuccess: suspend (uid: String) -> Unit = {}) =
        withContext(IO) {
            habitApi.add(AUTH_TOKEN, habit).invoke()?.let {
                fetchAllHabits()
                onSuccess(it.uid)
            }
        }

    suspend fun delete(habit: HabitItem, onSuccess: suspend () -> Unit = {}) = withContext(IO) {
        habitApi.delete(AUTH_TOKEN, HabitUid(habit.id)).invoke()?.let {
            onSuccess()
        }
    }
}