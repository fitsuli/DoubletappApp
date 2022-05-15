package ru.fitsuli.doubletappapp.data

import com.haroldadmin.cnradapter.invoke
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.fitsuli.doubletappapp.Utils.Companion.AUTH_TOKEN
import ru.fitsuli.doubletappapp.domain.models.HabitItem
import ru.fitsuli.doubletappapp.domain.models.HabitUid
import ru.fitsuli.doubletappapp.executeWithConfiguredRetry

class NetworkRepository {
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