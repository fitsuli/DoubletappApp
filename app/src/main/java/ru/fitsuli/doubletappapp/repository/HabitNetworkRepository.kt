package ru.fitsuli.doubletappapp.repository

import com.haroldadmin.cnradapter.invoke
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.Utils.Companion.AUTH_TOKEN
import ru.fitsuli.doubletappapp.executeWithConfiguredRetry
import ru.fitsuli.doubletappapp.model.HabitItem
import ru.fitsuli.doubletappapp.model.HabitUid

class HabitNetworkRepository {
    private val habitApi = RetrofitRequestApi.getInstance()

    suspend fun fetchAllHabits(
        onSuccess: suspend (List<HabitItem>) -> Unit = {},
        onError: suspend () -> Unit = {}
    ) =
        withContext(IO) {
            executeWithConfiguredRetry {
                habitApi.getAll(AUTH_TOKEN)
            }()?.let {
                onSuccess(it)
            } ?: onError()
        }

    suspend fun add(
        habit: HabitItem,
        onSuccess: suspend (uid: String) -> Unit = {},
        onError: suspend () -> Unit = {}
    ) =
        withContext(IO) {
            executeWithConfiguredRetry {
                habitApi.add(
                    AUTH_TOKEN, habit.copy(id = "") // required by the API
                )
            }()?.let {
                fetchAllHabits()
                onSuccess(it.uid)
            } ?: onError()
        }

    suspend fun update(
        habit: HabitItem,
        onSuccess: suspend (uid: String) -> Unit = {},
        onError: suspend () -> Unit = {}
    ) =
        withContext(IO) {
            executeWithConfiguredRetry { habitApi.add(AUTH_TOKEN, habit) }()?.let {
                fetchAllHabits()
                onSuccess(it.uid)
            } ?: onError()
        }

    suspend fun delete(
        habit: HabitItem,
        onSuccess: suspend () -> Unit = {},
        onError: suspend () -> Unit = {}
    ) =
        withContext(IO) {
            executeWithConfiguredRetry { habitApi.delete(AUTH_TOKEN, HabitUid(habit.id)) }()?.let {
                onSuccess()
            } ?: onError()
        }
}